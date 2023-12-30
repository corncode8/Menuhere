package booklet.menuhere.domain.menu.dtos;

import booklet.menuhere.domain.menu.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class MenuAddDto {
    @NotNull
    private String name;
    @NotNull
    private String content;
    @NotNull
    private int price;
    private Category category;
    @NotNull
    private MultipartFile attachFile;

}
