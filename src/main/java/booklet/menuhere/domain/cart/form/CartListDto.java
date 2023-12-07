package booklet.menuhere.domain.cart.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {

    public int cartTotalPrice;
    public int deliveryTip = 2000;
    List<CartDto> cartDto;

    public CartListDto(int cartTotalPrice, List<CartDto> cartDtos) {
        this.cartTotalPrice = cartTotalPrice;
        this.cartDto = cartDtos;
    }
}
