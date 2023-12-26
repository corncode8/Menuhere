package booklet.menuhere.domain.menu.form;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class MenuViewDto {

    private Long menuId;
    private String name;
    private String content;
    private int price;
    private UploadFile uploadFile;
    private Category category;

    public MenuViewDto(Menu menu) {
        menuId = menu.getId();
        name = menu.getName();
        content = menu.getContent();
        price = menu.getPrice();
        uploadFile = menu.getUploadFile();
        category = menu.getCategory();
    }

}
