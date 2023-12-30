package booklet.menuhere.domain.ordermenu;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderMenu extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderMenu_id")
    private Long id;

    private int totalPrice;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public OrderMenu(int totalPrice, int quantity, Order order, Menu menu) {
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.order = order;
        this.menu = menu;
    }

    public OrderMenu() {
    }
}
