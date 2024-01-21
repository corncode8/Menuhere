package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeOrderDto {
    @NotNull
    private OrderStatus status;
    @Nullable
    private String requests;
    @NotNull
    private int orderPrice;
    @NotNull
    private String orderType;
    @NotNull
    private String payType;
    @NotNull
    private String payStatus;
    @NotNull
    List<OrderMenuDto> orderMenus;

    @NotNull
    private Integer tableNo;
    @Nullable
    private String email;
}
