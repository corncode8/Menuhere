package booklet.menuhere;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
@RequiredArgsConstructor
public class MenuhereApplication {
	private final EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(MenuhereApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		//강제 지연 로딩 설정
		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5Module;
	}
	@Bean
	public JPAQueryFactory jpaQueryFactory() {

		return new JPAQueryFactory(em);
	}

}
