package booklet.menuhere.domain.menu.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class MenuAddForm {
    @NotNull
    private String name;
    @NotNull
    private String content;
    @NotNull
    private int price;
    @NotNull
    private MultipartFile attachFile;
}
