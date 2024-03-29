package booklet.menuhere.repository.order.query;

import booklet.menuhere.domain.QDelivery;
import booklet.menuhere.domain.User.QUser;
import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.order.QOrder;
import booklet.menuhere.domain.order.dtos.OrderSearchDto;
import booklet.menuhere.domain.order.dtos.OrderQueryDto;
import booklet.menuhere.domain.order.dtos.OrderViewMenuDto;
import booklet.menuhere.domain.ordermenu.QOrderMenu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderSearchRepository {

    private final JPAQueryFactory queryFactory;

    // findAll과 findOrderViewMenus를 분리하여 호출함으로써 N+1 쿼리 문제를 피함
    public Page<OrderQueryDto> findAll_optimization(OrderSearchDto searchDto, Pageable pageable) {
        Page<OrderQueryDto> queryResults = findAll(searchDto, pageable);

        List<OrderQueryDto> result = queryResults.getContent();
        long total = queryResults.getTotalElements();

        Map<Long, List<OrderViewMenuDto>> orderViewMenus = findOrderViewMenus(orderIds(result));

        result.forEach(o -> o.setOrderMenus(orderViewMenus.get(o.getOrderId())));

//        log.info("repository queryResults : {}", queryResults);
        return new PageImpl<>(result, pageable, total);
    }

    private List<Long> orderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    /**
     * toOne을 한번에 조회
     * */
    private Page<OrderQueryDto> findAll(OrderSearchDto searchDto, Pageable pageable) {

        QOrder order = QOrder.order;
        QUser user = QUser.user;
        QDelivery delivery = QDelivery.delivery;
        BooleanBuilder searchQuery = searchOrders(searchDto);

        List<OrderQueryDto> content = queryFactory
                .select(Projections.constructor(
                        OrderQueryDto.class,
                        order.id,
                        user.username,
                        order.orderType,
                        order.createdDate,
                        order.status,
                        delivery.address
                ))
                .from(order)
                // 명시적인 join - cross join을 방지
                .join(order.user, user)
                .join(order.delivery, delivery)
                .where(searchQuery)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(order.count())
                .from(order)
                .join(order.user, user)
                .join(order.delivery, delivery)
                .where(searchQuery)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 1:N 조회
     * */
    private Map<Long, List<OrderViewMenuDto>> findOrderViewMenus(List<Long> orderId) {

        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QMenu menu = QMenu.menu;
        QOrder order = QOrder.order;

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(orderMenu.order.id.in(orderId));

        List<OrderViewMenuDto> content = queryFactory
                .select(Projections.constructor(
                        OrderViewMenuDto.class,
                        orderMenu.order.id,
                        menu.name,
                        orderMenu.totalPrice,
                        orderMenu.quantity
                ))
                .from(orderMenu)
                .join(orderMenu.menu, menu)
                .join(orderMenu.order, order)
                .where(condition)
                .fetch();


        return content.stream()
                .collect(Collectors.groupingBy(OrderViewMenuDto::getOrderId));
    }


    private BooleanBuilder searchOrders(OrderSearchDto searchDto) {

        QOrder order = QOrder.order;

        BooleanExpression memberNameEq = searchDto.getUserName() != null ? order.user.username.like("%" + searchDto.getUserName() + "%") : null;
        BooleanExpression orderStatusEq = searchDto.getStatus() != null ? order.status.eq(searchDto.getStatus()) : null;
        BooleanExpression orderTypeEq = searchDto.getOrderType() != null ? order.orderType.eq(searchDto.getOrderType()) : null;

        BooleanBuilder condition = new BooleanBuilder();
        if (memberNameEq != null) {
            condition.and(memberNameEq);
        }
        if (orderStatusEq != null) {
            condition.and(orderStatusEq);
        }
        if (orderTypeEq != null) {
            condition.and(orderTypeEq);
        }

        if (searchDto.getStartTime() != null && searchDto.getEndTime() != null) {
            condition.or(order.createdDate.between(
                    searchDto.getStartTime().atStartOfDay(),
                    searchDto.getEndTime().atTime(23, 59, 59)
            ));
        } else if (searchDto.isEmpty()) {
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(3);
            condition.and(order.createdDate.after(localDateTime));
        }


//        log.info(query.stream().toList().toString());
        return condition;
    }

}
