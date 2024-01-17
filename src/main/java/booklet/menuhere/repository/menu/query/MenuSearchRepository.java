package booklet.menuhere.repository.menu.query;

import booklet.menuhere.domain.menu.QMenu;
import booklet.menuhere.domain.menu.dtos.MenuViewDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static booklet.menuhere.domain.menu.QMenu.*;

@Repository
@RequiredArgsConstructor
public class MenuSearchRepository {

    private final JPAQueryFactory queryFactory;

    public Page<MenuViewDto> findAll(String search, Pageable pageable) {

        JPAQuery<Long> cntQuery = getCount(search);

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
                .where(nameLike(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> cntQuery.fetchOne());
    }

    private BooleanExpression nameLike(String search) {
        if (!StringUtils.hasText(search)) {
//            return null;
            return Expressions.asBoolean(true).isTrue(); // 항상 참인 조건 반환
        }
        return menu.name.like("%" + search + "%");
    }

    private JPAQuery<Long> getCount(String search) {
        QMenu menu = QMenu.menu;

        BooleanExpression condition = nameLike(search);

        JPAQuery<Long> cntQuery = queryFactory
                .select(menu.count())
                .from(menu)
                .where(condition);

        // JPQL 쿼리 리턴
        return cntQuery;
    }
}
