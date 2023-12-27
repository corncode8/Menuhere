package booklet.menuhere.domain.order;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.OrderMenu;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.orderStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private orderStatus orderStatus;

    private String requests;

    private int orderPrice;
    private int tableNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    // 주문 유형 ( 예약, 배달, 매장 내 식사 )
    private String orderType;


}
