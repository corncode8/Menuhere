package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.model.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderViewDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; // 주문시간
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderViewMenuDto> orderItems;

    public OrderViewDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderViewDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderViewMenuDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }
}
