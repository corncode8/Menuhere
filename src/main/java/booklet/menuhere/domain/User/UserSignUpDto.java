package booklet.menuhere.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
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
}
