package booklet.menuhere.domain.order;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.Delivery;
import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.Payment;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.OrderStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String requests;

    private int orderPrice;
    private int tableNo;

    // 주문 유형 (예약, 배달, 매장 내 식사)
    private String orderType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public void createOrder(OrderStatus orderStatus, String requests, int orderPrice, int tableNo, String orderType, Payment payment) {
        this.orderStatus = orderStatus;
        this.requests = requests;
        this.orderPrice = orderPrice;
        this.tableNo = tableNo;
        this.orderType = orderType;
        this.payment = payment;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
