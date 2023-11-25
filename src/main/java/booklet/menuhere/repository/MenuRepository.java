package booklet.menuhere.repository;


import booklet.menuhere.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByName(String name);
    Optional<Menu> findById(Long id);
    List<Menu> findAll();


}
