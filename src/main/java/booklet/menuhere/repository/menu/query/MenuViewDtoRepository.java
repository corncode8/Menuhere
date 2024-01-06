package booklet.menuhere.repository.menu.query;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.menu.dtos.MenuViewDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
//    public List<MenuViewDto> findViewDtoV1() {
//        return em.createQuery(
//                "select new booklet.menuhere.domain.menu.dtos.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
//                        " from Menu m", MenuViewDto.class)
//                .getResultList();
//    }

    private JPAQuery<Menu> BaseMenuQuery() {
        QMenu menu = QMenu.menu;
        JPAQuery<Menu> menuBase = queryFactory
                .selectFrom(menu)
                .where(menu.sale.isFalse());
        return menuBase;
    }

    private JPAQuery<Long> getCount() {
        QMenu menu = QMenu.menu;

        JPAQuery<Long> cntQuery = queryFactory
                .select(menu.count())
                .from(menu)
                .where(menu.sale.isFalse());

        // JPQL 쿼리 리턴
        return cntQuery;
    }

    private List<MenuViewDto> getMenuViewDto(Pageable pageable) {
        QMenu menu = QMenu.menu;

        List<MenuViewDto> content = queryFactory
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
                .where(menu.sale.isFalse())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    // QueryDSL 사용
    public Page<MenuViewDto> menuViewQuery(Pageable pageable) {
        List<MenuViewDto> menuViewDto = getMenuViewDto(pageable);
        JPAQuery<Long> cntQuery = getCount();

        // PageableExecutionUtils가 내부적으로 count 쿼리가 필요없으면 조회해오지 않는다.
        return PageableExecutionUtils.getPage(menuViewDto, pageable, () -> cntQuery.fetchOne());
//        return new PageImpl<>(menuViewDto, pageable, cnt);
    }


    public List<MenuViewDto> findCategoryView(Category category) {
        return em.createQuery(
                "select new booklet.menuhere.domain.menu.dtos.MenuViewDto(m.id, m.name, m.content, m.price, m.uploadFile, m.category)" +
                        " from Menu m" +
                        " where m.category = :category", MenuViewDto.class)
                .setParameter("category", category)
                .getResultList();
    }
}
