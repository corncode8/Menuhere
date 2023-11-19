package booklet.menuhere.config.auth;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.Address;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * DefaultOAuth2User를 상속하고, email과 role 필드를 추가로 가진다.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private Address address;
    private String phone;
    private String email;
    private Role role;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     *
     *  super()로 부모 객체인 DefaultOAuth2User를 생성하고,
     *  email과 role 파라미터를 추가로 받아서, 주입하여 CustomOAuth2User를 생성합니다.
     */

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role, Address address, String phone) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
        this.address = address;
        this.phone = phone;
    }
}
