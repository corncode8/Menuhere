package booklet.menuhere.domain.User;

import booklet.menuhere.domain.Role;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String phone;
    @NotNull
    private String password;

    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp modifiedDate;

    private Role role;
    @Embedded
    private Address address;

//    private String encryptedPassword(String password) {
//        return BcryptPasswordEncoder.encrypt(password);
//    }
}
