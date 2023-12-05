package booklet.menuhere.domain.cart.form;

import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;

@Data
public class ImageDto {
    private String name;
    private UploadFile uploadFile;
}
