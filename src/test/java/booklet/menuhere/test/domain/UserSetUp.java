package booklet.menuhere.test.domain;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.dtos.UserSignUpDto;
import booklet.menuhere.domain.model.Address;
import booklet.menuhere.domain.model.Email;
import booklet.menuhere.repository.UserRepository;
import booklet.menuhere.test.config.TestProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile(TestProfile.TEST)
@Component
public class UserSetUp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    Address address = new Address("12345", "testDefalutAddress", "testDetailAddress");
    UserSignUpDto signUpDto = new UserSignUpDto("testemail@test.com", "Password123!", "testUsername", "010-1234-5678", address);

    public User save() {
        final User user = buildUser(signUpDto);
        return userRepository.save(user);
    }

    public User build() {
        return buildUser(signUpDto);
    }

    private User buildUser(UserSignUpDto signUpDto) {
        User user = User.builder()
                .email(Email.of(signUpDto.getEmail()))
                .username(signUpDto.getUsername())
                .phone(signUpDto.getPhone())
                .password(signUpDto.getPassword())
                .address(signUpDto.getAddress())
                .build();
        String refreshToken = jwtService.createRefreshToken();
        user.authorizeUser(); user.passwordEncode(passwordEncoder); user.updateRefreshToken(refreshToken);
        return user;
    }
}
