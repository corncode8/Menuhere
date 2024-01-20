package booklet.menuhere.repository.order.query;

import booklet.menuhere.domain.QDelivery;
import booklet.menuhere.domain.User.QUser;
import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.order.QOrder;
import booklet.menuhere.domain.order.dtos.OrderQueryDto;
import booklet.menuhere.domain.order.dtos.OrderViewMenuDto;
import booklet.menuhere.domain.ordermenu.QOrderMenu;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    // findAll과 findOrderViewMenus를 분리하여 호출함으로써 N+1 쿼리 문제를 피함
    public List<OrderQueryDto> findAll_optimization() {
        List<OrderQueryDto> result = findAll();

        Map<Long, List<OrderViewMenuDto>> orderViewMenus = findOrderViewMenus(orderIds(result));

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
    private List<OrderQueryDto> findAll() {

        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        QOrder order = QOrder.order;
        QUser user = QUser.user;
        QDelivery delivery = QDelivery.delivery;

        List<OrderQueryDto> content = queryFactory
                .select(Projections.constructor(
                        OrderQueryDto.class,
                        order.id,
                        user.username,
                        order.orderType,
                        order.createdDate,
                        order.orderStatus,
                        delivery.address
                ))
                .from(order)
                // 명시적인 join - cross join을 방지
                .join(order.user, user)
                .join(order.delivery, delivery)
                .where(order.createdDate.before(threeMonthsAgo))
                .fetch();

        return content;
    }

    /**
     * 1:N 조회
     * */
    private Map<Long, List<OrderViewMenuDto>> findOrderViewMenus(List<Long> orderId) {

        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QMenu menu = QMenu.menu;

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
                .where(orderMenu.order.id.in(orderId))
                .fetch();
        return content.stream()
                .collect(Collectors.groupingBy(OrderViewMenuDto::getOrderId));
    }



//    private JPAQuery<Long> getCount(LocalDateTime startDate) {
//        QOrder order = QOrder.order;
//
//        JPAQuery<Long> cntQuery = queryFactory
//                .select(order.count())
//                .from(order)
//                .where(order.createdDate.after(startDate));
//
//        // JPQL 쿼리 리턴
//        return cntQuery;
//    }

}
