package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.OrderMenu;
import booklet.menuhere.domain.menu.file.UploadFile;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Menu extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private String content;
    private int price;
    private int orderNum = 0;               // 메뉴 주문 횟수
    private boolean isdel = false;          // 삭제여부 :     메뉴삭제시 true
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uploadFile_id")
    private UploadFile uploadFile;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuOption> menuOptions = new ArrayList<>();


}
