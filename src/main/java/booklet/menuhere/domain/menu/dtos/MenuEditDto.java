package booklet.menuhere.domain.menu.dtos;

import booklet.menuhere.domain.menu.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuEditDto {

    private String name;
    private String content;
    private int price;
    private boolean saleHold;
    private MultipartFile attachFile;
    private String storeFileName;
    private Category category;

}
