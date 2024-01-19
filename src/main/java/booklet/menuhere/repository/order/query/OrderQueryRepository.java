package booklet.menuhere.repository.order.query;

import booklet.menuhere.domain.order.QOrder;
import booklet.menuhere.domain.order.dtos.OrderViewDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public Page<OrderViewDto> findAll() {

        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        return (Page<OrderViewDto>) em.createQuery(
                "select new booklet.menuhere.domain.order.dtos.OrderViewDto(o.id, u.username, o.createdDate, o.orderStatus, d.address)"+
                        " from Order o" +
                        " join o.user u" +
                        " join o.delivery d", OrderViewDto.class)
                .getResultList();
    }

    private JPAQuery<Long> getCount(LocalDateTime startDate) {
        QOrder order = QOrder.order;

        JPAQuery<Long> cntQuery = queryFactory
                .select(order.count())
                .from(order)
                .where(order.createdDate.after(Timestamp.valueOf(startDate)));

        // JPQL 쿼리 리턴
        return cntQuery;
    }

}
