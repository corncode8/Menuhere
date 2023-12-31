package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.orderStatus;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeOrderDto {
    private orderStatus orderStatus;
    private String requests;
    private int orderPrice;
    private String orderType;
    private String payType;
    private String payStatus;
    List<OrderMenuDto> orderMenus;

    private Integer tableNo;
    private String email;
}
