package booklet.menuhere.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class MenuOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuoption_id")
    private Long id;

    private String option;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;
}
