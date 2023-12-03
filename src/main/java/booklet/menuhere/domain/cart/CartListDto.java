package booklet.menuhere.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {

    @NotNull
    public int cartTotalPrice;
//    private int deliveryTip;
    List<CartDto> cartDto;
}
