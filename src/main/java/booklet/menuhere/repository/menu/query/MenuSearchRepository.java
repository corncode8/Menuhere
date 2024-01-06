package booklet.menuhere.repository.menu.query;

import booklet.menuhere.domain.menu.Menu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static booklet.menuhere.domain.menu.QMenu.*;

@Repository
@RequiredArgsConstructor
public class MenuSearchRepository {

    private final JPAQueryFactory queryFactory;

    public List<Menu> findAll(String search) {

        return queryFactory
                .select(menu)
                .from(menu)
                .where(nameLike(search))
                .limit(10)
                .fetch();
    }

    private BooleanExpression nameLike(String search) {
        if (!StringUtils.hasText(search)) {
//            return null;
            return Expressions.asBoolean(true).isTrue(); // 항상 참인 조건 반환
        }
        return menu.name.like("%" + search + "%");
    }
}
