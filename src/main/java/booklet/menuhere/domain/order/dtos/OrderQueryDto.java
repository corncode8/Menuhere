package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.model.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String username;                // 주문자 이름
    private String orderType;           // 주문 유형 (예약, 배달, 매장 내 식사)
    private LocalDateTime orderDate;    // 주문시간
    private OrderStatus orderStatus;    // 주문, 배달중, 배달완료, 주문취소
    private Address address;            // 주소
    private List<OrderViewMenuDto> orderMenus;

    public OrderQueryDto(Long orderId, String username, String orderType , LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.username = username;
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
