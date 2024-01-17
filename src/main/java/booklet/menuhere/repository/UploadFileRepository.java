package booklet.menuhere.repository;

import booklet.menuhere.domain.menu.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

}
