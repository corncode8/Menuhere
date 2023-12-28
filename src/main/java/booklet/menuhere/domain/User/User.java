package booklet.menuhere.domain.User;

import booklet.menuhere.domain.BaseEntity;
import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.order.Order;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String phone;

    private String password;

    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

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
