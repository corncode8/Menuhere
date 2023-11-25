package booklet.menuhere.repository;

import booklet.menuhere.domain.menu.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    Optional<UploadFile> findById(long id);
}
