package booklet.menuhere.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private String content;
    private int price;
    private String imgPath;
    private int orderNum = 0;               // 메뉴 주문 횟수
    private boolean isdel = false;          // 삭제여부 :     메뉴삭제시 true

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuOption> menuOptions = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;
}
