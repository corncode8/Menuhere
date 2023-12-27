package booklet.menuhere.domain.cart.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {

    public int cartTotalPrice;
    public int deliveryTip;
    List<CartDto> cartDto;

    public CartListDto(int cartTotalPrice, List<CartDto> cartDtos) {
        this.cartTotalPrice = cartTotalPrice;
        this.cartDto = cartDtos;
    }

    public CartListDto() {
    }
}
