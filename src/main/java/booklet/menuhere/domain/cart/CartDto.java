package booklet.menuhere.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class CartDto {

    private String menuName;
    private int menuPrice;
    private int amount;
    private int totalPrice;

    public void totalPriceCalc() {
        this.totalPrice = menuPrice * amount;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDTO = (CartDto) o;
        return menuPrice == cartDTO.menuPrice && amount == cartDTO.amount &&
                totalPrice == cartDTO.totalPrice && Objects.equals(menuName, cartDTO.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName, menuPrice, amount, totalPrice);
    }
}
