package booklet.menuhere.domain.menu.form;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuEditDto {

    private Long menuId;
    private String name;
    private String content;
    private int price;
    private boolean saleHold;
    private boolean sale;
    private MultipartFile attachFile;
    private String storeFileName;
    private Category category;

}
