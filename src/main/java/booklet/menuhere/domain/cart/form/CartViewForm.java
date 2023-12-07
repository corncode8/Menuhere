package booklet.menuhere.domain.cart.form;

import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;

@Data
public class CartViewForm {
    private Long menuId;
    private String name;
    private int price;
    private int amount;
    private UploadFile uploadFile;


}
