package booklet.menuhere.domain.menu.file;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.menu.Menu;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UploadFile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uploadFile_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;


    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile() {
    }
}
