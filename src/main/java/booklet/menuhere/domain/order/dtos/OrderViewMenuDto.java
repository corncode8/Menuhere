package booklet.menuhere.domain.order.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderViewMenuDto {

    @JsonIgnore
    private Long orderId;       // 주문번호
    private String menuName;    // 상품명
    private int orderPrice;     // 총 주문 가격
    private int quantity;          // 총 주문 수량

    public OrderViewMenuDto(Long orderId, String menuName, int orderPrice, int quantity) {
        this.orderId = orderId;
        this.menuName = menuName;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }
}
