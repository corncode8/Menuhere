package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.menu.file.UploadFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Menu extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", updatable = false)
    private Long id;

    private String name;
    private String content;
    private int price;
    private int orderNum = 0;       // 메뉴 주문 횟수
    private boolean sale;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uploadFile_id")
    private UploadFile uploadFile;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
//    private List<MenuOption> menuOptions = new ArrayList<>();


    public void UpdateMenu(String name, String content, int price, Category category, boolean sale, UploadFile uploadFile) {
        this.name = name;
        this.content = content;
        this.category = category;
        this.sale = sale;
        this.uploadFile = uploadFile;
        this.price = price;
    }
    public void EditMenu(String name, String content, int price, Category category, boolean saleHold) {
        this.name = name;
        this.content = content;
        this.category = category;
        this.sale = saleHold;
        this.price = price;
    }
    public void updateFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void plusOrderNum(int num) {
        this.orderNum += num;
    }

}
