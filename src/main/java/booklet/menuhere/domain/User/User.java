package booklet.menuhere.domain.User;

import booklet.menuhere.domain.OrderMenu;
import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.order.Order;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// form 사용시 validation 원복 -> form에 적용
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String phone;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;


    @Enumerated(EnumType.STRING)
    private Role role;              // USER, MANAGER
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // GITHUB, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken;
    @Embedded
    private Address address;

    public void authorizeUser() {
        this.role = Role.USER;
    }
    public void passwordEncode (PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
