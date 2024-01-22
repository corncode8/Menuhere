package booklet.menuhere.repository.order;

import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Spring Data JPA
    List<Order> findByUserUsernameContainingAndStatusAndOrderType(String username, OrderStatus status, String orderType);

    // @Query
    @Query("SELECT o FROM Order o JOIN FETCH o.user u JOIN FETCH o.delivery d WHERE u.username LIKE %:username% AND o.status = :status AND o.orderType = :orderType")
    List<Order> findOrdersWithDetails(@Param("username") String username, @Param("status") OrderStatus status, @Param("orderType") String orderType);

}
