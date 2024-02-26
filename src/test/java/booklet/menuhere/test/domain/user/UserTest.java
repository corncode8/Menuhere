package booklet.menuhere.test.domain.user;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.test.IntegrationTest;
import booklet.menuhere.test.config.TestProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles(TestProfile.TEST)
@Slf4j
class UserTest extends IntegrationTest {

    @DisplayName("유저 권한 테스트 (유저 -> 매니저)")
    @Test
    void authorizeManagerTest() {
        //given
        User user = userSetUp.save();

        //when
        user.authorizeManager();

        //then
        assertThat(user.getRole()).isEqualTo(Role.MANAGER);
    }

    @DisplayName("유저 권한 테스트 (매니저 -> 유저)")
    @Test
    void authorizeUserTest() {
        //given
        User user = userSetUp.saveManager();

        //when
        user.authorizeUser();

        //then
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @DisplayName("passwordEncodeTest")
    @Test
    void passwordEncodeTest() {
        //given & when
        User user = userSetUp.save();
        final String password = "Password123!";

        //then
        assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();
    }

}