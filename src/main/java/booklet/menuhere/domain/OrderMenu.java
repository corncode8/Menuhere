package booklet.menuhere.domain;

import booklet.menuhere.domain.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class OrderMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderMenu_id")
    private Long id;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;



    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;
}
