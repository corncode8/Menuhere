package booklet.menuhere.domain.menu;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MenuOption{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_option_id", updatable = false)
    private Long id;

    @Column(name = "options")
    private String option;
    private int price;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

}
