package booklet.menuhere.domain.menu.form;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;

@Data
public class MenuViewForm {

    private String name;
    private String content;
    private int price;
    private UploadFile uploadFile;
    private Category category;

}
