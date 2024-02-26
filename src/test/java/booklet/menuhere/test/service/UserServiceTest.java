package booklet.menuhere.test.service;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.model.Address;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.dtos.UserSignUpDto;
import booklet.menuhere.domain.model.Email;
import booklet.menuhere.domain.order.dtos.OrderUserInfoDto;
import booklet.menuhere.exception.CustomException;
import booklet.menuhere.test.IntegrationTest;
import booklet.menuhere.test.config.TestProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles(TestProfile.TEST)
@Slf4j
class UserServiceTest extends IntegrationTest {

    @DisplayName("회원가입 성공")
    @Test
    void signUpTest() throws Exception {
        //given
        final Address address = new Address("12345", "testDefalutAddress", "testDetailAddress");
        final UserSignUpDto userSignUpDto = new UserSignUpDto("test@test.com", "Password123",
                "testUsername", "010-1234-5678", address);


        //when
        userService.signUp(userSignUpDto);

        //then
        Optional<User> newUser = userRepository.findByEmailValue("test@test.com");
        assertTrue(newUser.isPresent());
        assertEquals("testUsername", newUser.get().getUsername());
        assertEquals("010-1234-5678", newUser.get().getPhone());
        assertEquals(address, newUser.get().getAddress());
    }

    @DisplayName("회원가입 실패 (중복 이메일)")
    @Test
    void signUpDuplicateEmail() {
        //given
        final Address address = new Address("12345", "testDefalutAddress", "testDetailAddress");
        final UserSignUpDto userSignUpDto = new UserSignUpDto("test@test.com", "Password123",
                "testUsername", "010-1234-5678", address);
        User user = User.builder()
                .email(Email.of("test@test.com"))
                .build();
        userRepository.save(user);

        //when & then
        assertThrows(Exception.class, () -> userService.signUp(userSignUpDto));
    }

    @DisplayName("회원가입 실패 (중복 이름)")
    @Test
    void signUpDuplicateUsername() {
        //given
        final Address address = new Address("12345", "testDefalutAddress", "testDetailAddress");
        final UserSignUpDto userSignUpDto = new UserSignUpDto("test@test.com", "Password123",
                "testUsername", "010-1234-5678", address);
        User user = User.builder()
                .email(Email.of("test1234@test.com"))
                .username("testUsername")
                .build();
        userRepository.save(user);

        //when & then
        assertThrows(Exception.class, () -> userService.signUp(userSignUpDto));
    }

    @DisplayName("로그인 테스트 (성공)")
    @Test
    void LoginTest() throws CustomException {
        // given
        final User user = userSetUp.save();
        final String password = "Password123!";


        // when
        User login = userService.login(user.getEmail().getValue(), password);

        // then
        log.info(user.getEmail().getValue());
        log.info(String.valueOf(login));
        assertThat(login).isNotNull();
        assertEquals(user.getEmail().getValue(), "testemail@test.com");
    }

    @DisplayName("로그인 실패 (아이디 불일치)")
    @Test
    void LoginIdMismatch() {
        // given
        final String nonExistentEmail = "nonexistent@test.com";
        final String password = "Password123!";

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            userService.login(nonExistentEmail, password);
        }, "해당 아이디를 찾을 수 없습니다: " + nonExistentEmail);
    }

    @DisplayName("로그인 실패 (비밀번호 불일치)")
    @Test
    void LoginPasswordMismatch() throws Exception {
        // given
        final User user = userSetUp.save();
        final String password = "failPassword123!";

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            userService.login(user.getEmail().getValue(), password);
        }, "비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("getRole 성공")
    @Test
    void getRoleTest() {
        // given
        final User user = userSetUp.save();

        // when
        Role role = userService.getRole(user.getEmail().getValue());

        // then
        log.info(String.valueOf(role));
        assertThat(role).isNotNull();
        assertThat(role).isEqualTo(Role.USER);
    }

    @DisplayName("getRole 실패")
    @Test
    void failGetRoleTest() {
        // given
        final String nonUser = "NonUser@test.com";

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            userService.getRole(nonUser);
        }, "해당 아이디를 찾을 수 없습니다. " + nonUser);
    }

    @DisplayName("주문 고객 정보 api 테스트 성공")
    @Test
    void getUserInfo() {
        // given
        final User user = userSetUp.save();

        // when
        OrderUserInfoDto userInfoDto = userService.OrderUserInfo(user.getEmail().getValue());

        // then
        log.info(String.valueOf(userInfoDto));
        assertThat(userInfoDto).isNotNull();
        assertThat(userInfoDto.getEmail()).isEqualTo(user.getEmail().getValue());
        assertThat(userInfoDto.getUsername()).isEqualTo(user.getUsername());
    }

    @DisplayName("주문 고객 정보 api 테스트 실패")
    @Test
    void failGetUserInfo() {
        // given
        final String nonExistentEmail = "nonexistent@example.com";

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            userService.OrderUserInfo(nonExistentEmail);
        }, "해당 아이디를 찾을 수 없습니다. " + nonExistentEmail);

    }


}
