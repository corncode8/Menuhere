package booklet.menuhere.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartListDto {

    public int cartTotalPrice;
//    private int deliveryTip;
    List<CartDto> cartDto;
}
