package booklet.menuhere.repository.menu.query;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import com.querydsl.core.types.Projections;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@RequiredArgsConstructor
public class MenuViewDtoRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;




    // fit하기 때문에 재사용성이 좋지 않다. 가장 최적화 조회 상태.
    public List<MenuViewDto> findViewDtoV1() {
        return em.createQuery(
                "select new booklet.menuhere.domain.menu.form.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
                        " from Menu m", MenuViewDto.class)
                .getResultList();
    }

    // QueryDSL 사용
    public List<MenuViewDto> findViewDtoV2() {
        QMenu menu = QMenu.menu;

        return queryFactory
                .select(Projections.constructor(
                        MenuViewDto.class,
                        menu.id,
                        menu.name,
                        menu.content,
                        menu.price,
                        menu.uploadFile,
                        menu.category
                ))
                .from(menu)
                .fetch();
    }

// 백단에서 잘 넘기고 있는지 log 확인
// 프론트에서 어떻게 받고 있는지 log 확인
    public List<MenuViewDto> findCategoryView(Category category) {
        return em.createQuery(
                "select new booklet.menuhere.domain.menu.form.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
                        " from Menu m" +
                        " where m.category = :category", MenuViewDto.class)
                .setParameter("category", category)
                .getResultList();
    }
}
