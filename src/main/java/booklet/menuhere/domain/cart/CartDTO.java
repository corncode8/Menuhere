package booklet.menuhere.domain.cart;

import booklet.menuhere.domain.menu.form.MenuViewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDTO {
    private int cartTotal;
    List<MenuViewDTO> menuDTO;
}
