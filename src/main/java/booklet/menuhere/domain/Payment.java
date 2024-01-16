package booklet.menuhere.domain;

import booklet.menuhere.domain.order.Order;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", updatable = false)
    private Long id;

    private String payType;
    private String payStatus;
    private int orderPrice;

    @OneToOne(mappedBy = "payment")
    private Order order;

    public void createPayment(String payType, String payStatus, int orderPrice, Order order) {
        this.payType = payType;
        this.payStatus = payStatus;
        this.orderPrice = orderPrice;
        this.order = order;
    }

}
