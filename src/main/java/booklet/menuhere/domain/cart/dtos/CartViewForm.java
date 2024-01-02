package booklet.menuhere.domain.cart.dtos;

import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;

@Data
public class CartViewForm {
    private Long menuId;
    private String name;
    private int price;
    private int amount;
    private UploadFile uploadFile;

    public void setCartViewForm(Long menuId, String name, int price, int amount, UploadFile uploadFile) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.uploadFile = uploadFile;
    }
}
