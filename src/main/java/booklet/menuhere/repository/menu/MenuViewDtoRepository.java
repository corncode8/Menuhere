package booklet.menuhere.repository.menu;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MenuViewDtoRepository {

    private final EntityManager em;

    public List<MenuViewDto> findViewDto() {
        return em.createQuery(
                "select new booklet.menuhere.domain.menu.form.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
                        " from Menu m", MenuViewDto.class)
                .getResultList();
    }

    public List<MenuViewDto> findCategoryView(Category category) {
        return em.createQuery(
                "select new booklet.menuhere.domain.menu.form.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
                        " from Menu m" +
                        " where m.category = :category", MenuViewDto.class)
                .setParameter("category", category)
                .getResultList();
    }
}
