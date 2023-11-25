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

    @OneToOne(mappedBy = "uploadFile", fetch = FetchType.LAZY)
    private Menu menu;

    public UploadFile(String originalFilename, String storeFileName) {
    }

    public UploadFile() {
    }
}
