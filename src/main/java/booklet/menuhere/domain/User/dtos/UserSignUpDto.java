package booklet.menuhere.domain.User.dtos;

import booklet.menuhere.domain.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Getter
public class UserSignUpDto {
    @Valid
    private String email;
    @Valid
    private String password;
    @Valid
    private String username;
    @Valid
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
