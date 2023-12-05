package booklet.menuhere.domain.cart.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
public class CartDto {

    @NotNull
    private String menuName;
    @NotNull
    private int menuPrice;
    @NotNull
    private int amount;
    private int totalPrice;


    public CartDto() {
        this.totalPrice = 0;
    }

    public void totalPriceCalc() {
        this.totalPrice = menuPrice * amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDTO = (CartDto) o;
        return menuPrice == cartDTO.menuPrice && Objects.equals(menuName, cartDTO.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName, menuPrice);
    }
}
