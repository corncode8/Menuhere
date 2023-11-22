package booklet.menuhere.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSignUpDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @NotBlank
    private String phone;
    private Address address;

    @Override
    public String toString() {
        return "UserSignUpDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}
