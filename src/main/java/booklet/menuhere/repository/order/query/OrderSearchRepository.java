package booklet.menuhere.repository.order.query;

import booklet.menuhere.domain.QDelivery;
import booklet.menuhere.domain.User.QUser;
import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.QOrder;
import booklet.menuhere.domain.order.dtos.OrderSearchDto;
import booklet.menuhere.domain.order.dtos.OrderQueryDto;
import booklet.menuhere.domain.order.dtos.OrderViewMenuDto;
import booklet.menuhere.domain.ordermenu.QOrderMenu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderSearchRepository {

    private final JPAQueryFactory queryFactory;


    public List<OrderQueryDto> findAll_optimization(OrderSearchDto searchDto) {
        List<OrderQueryDto> result = findAll(searchDto);

        Map<Long, List<OrderViewMenuDto>> orderViewMenus = findOrderViewMenus(orderIds(result), searchDto);

        result.forEach(o -> o.setOrderMenus(orderViewMenus.get(o.getOrderId())));

        return result;
    }

    private List<Long> orderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    /**
     * toOne을 한번에 조회
     * */
    private List<OrderQueryDto> findAll(OrderSearchDto searchDto) {

        QOrder order = QOrder.order;
        QUser user = QUser.user;
        QDelivery delivery = QDelivery.delivery;
        JPAQuery<Order> searchQuery = searchOrders(searchDto);

        List<OrderQueryDto> content = searchQuery
                .select(Projections.constructor(
                        OrderQueryDto.class,
                        order.id,
                        user.username,
                        order.orderType,
                        order.createdDate,
                        order.orderStatus,
                        delivery.address
                ))
                // 명시적인 join - cross join을 방지
                .from(order)
                .join(order.user, user)
                .join(order.delivery, delivery)
                .fetch();

        return content;
    }

    /**
     * 1:N 조회
     * */
    private Map<Long, List<OrderViewMenuDto>> findOrderViewMenus(List<Long> orderId, OrderSearchDto searchDto) {

        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QMenu menu = QMenu.menu;
        QOrder order = QOrder.order;

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(orderMenu.order.id.in(orderId));

        if (searchDto.getUserName() != null) {
            condition.and(order.user.username.eq(searchDto.getUserName()));
        }

        if (searchDto.getOrderType() != null) {
            condition.and(order.orderType.eq(searchDto.getOrderType()));
        }

        if (searchDto.getOrderStatus() != null) {
            condition.and(order.orderStatus.eq(searchDto.getOrderStatus()));
        }

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


    private JPAQuery<Order> searchOrders(OrderSearchDto searchDto) {

        QOrder order = QOrder.order;

        BooleanExpression memberNameEq = searchDto.getUserName() != null ? order.user.username.eq(searchDto.getUserName()) : null;
        BooleanExpression orderStatusEq = searchDto.getOrderStatus() != null ? order.orderStatus.eq(searchDto.getOrderStatus()) : null;
        BooleanExpression orderTypeEq = searchDto.getOrderType() != null ? order.orderType.eq(searchDto.getOrderType()) : null;

        BooleanBuilder condition = new BooleanBuilder(memberNameEq).and(orderStatusEq).and(orderTypeEq);

        if (searchDto.getStartTime() != null) {
            condition.and(order.createdDate.after(searchDto.getStartTime()));
        }
        if (searchDto.getEndTime() != null) {
            condition.and(order.createdDate.before(searchDto.getEndTime()));
        }

        JPAQuery<Order> query = queryFactory
                .selectFrom(order)
                .where(condition);

        return query;
    }

}
