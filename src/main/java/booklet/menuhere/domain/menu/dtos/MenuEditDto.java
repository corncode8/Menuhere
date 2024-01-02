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

    public void setMenuEditDto(String name, String content, int price, boolean saleHold, String storeFileName, Category category) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.saleHold = saleHold;
        this.storeFileName = storeFileName;
        this.category = category;
    }
}
