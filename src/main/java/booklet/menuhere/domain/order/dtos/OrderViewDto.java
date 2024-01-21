package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderViewDto {

    private Long orderId;
    private String username;
    private List<OrderViewMenuDto> orderMenus;
    private OrderStatus status;
    private String orderType;
    private LocalDateTime orderDate;
}
