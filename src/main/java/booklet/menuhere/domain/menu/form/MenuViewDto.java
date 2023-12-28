package booklet.menuhere.domain.menu.form;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MenuViewDto {

    private Long menuId;
    private String name;
    private String content;
    private int price;
    private UploadFile uploadFile;
    private Category category;

    public MenuViewDto(Long menuId, String name, String content, int price, UploadFile uploadFile, Category category) {
        this.menuId = menuId;
        this.name = name;
        this.content = content;
        this.price = price;
        this.uploadFile = uploadFile;
        this.category = category;
    }
}
