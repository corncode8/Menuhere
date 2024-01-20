package booklet.menuhere.test.service;

import booklet.menuhere.domain.User.User;
import booklet.menuhere.test.IntegrationTest;
import booklet.menuhere.test.config.TestProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.verify;

@ActiveProfiles(TestProfile.TEST)
@Slf4j
class JwtServiceTest extends IntegrationTest {

    private String secretKey = "testSecretKey";
    private String accessHeader = "Authorization";
    private String refreshHeader = "Authorization-refresh";
    private String accessToken = "";
    private String refreshToken = "";
    private String email = "";

    @BeforeEach
    void setUp() {
        User user = userSetUp.save();

        email = user.getEmail().getValue();
        accessToken = "Bearer " + jwtService.createAccessToken(user.getEmail().getValue());
        refreshToken = "Bearer " + jwtService.createRefreshToken();

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + accessToken);
        Mockito.when(request.getHeader("Authorization-refresh")).thenReturn("Bearer " + refreshToken);
    }

    @DisplayName("AccessToken 생성 테스트")
    @Test
    void createAccessToken() {
        //given & when
        String accessTokenTest = jwtService.createAccessToken(email);

        //then
        log.info(accessTokenTest);
        assertThat("Bearer " + accessTokenTest).isEqualTo(accessToken);
    }

    @DisplayName("RefreshToken 생성 테스트")
    @Test
    void createRefreshToken() {
        //given & when
        String refreshToken = jwtService.createRefreshToken();

        //then
        log.info(refreshToken);
        assertThat(refreshToken).isNotNull();
    }

    @DisplayName("AccessToken 헤더에 실어서 보내기 테스트")
    @Test
    void sendAccessToken() {
        //given & when
        jwtService.sendAccessToken(response, accessToken);

        //then
        verify(response).setHeader(eq(accessHeader), eq(accessToken));
    }

    @DisplayName("AccessToken + RefreshToken 헤더에 실어서 보내기 테스트")
    @Test
    void sendAccessAndRefreshToken() {
        //given & when
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        //then
        verify(response).setHeader(eq(accessHeader), eq(accessToken));
        verify(response).setHeader(eq(refreshHeader), eq(refreshToken));
    }

    @DisplayName("헤더에서 RefreshToken 추출 테스트")
    @Test
    void extractRefreshToken() {
        //given & when

        Optional<String> token = jwtService.extractRefreshToken(request);
        when(request.getHeader(refreshHeader)).thenReturn(refreshToken);

        //then
        log.info(token.toString());
        assertThat(token).contains(refreshToken.replace("Bearer ", ""));
    }

    @DisplayName("헤더에서 AccessToken 추출 테스트")
    @Test
    void extractAccessToken() {
        //given & when

        Optional<String> token = jwtService.extractAccessToken(request);
        when(request.getHeader(accessHeader)).thenReturn(accessToken);

        //then
        log.info("토큰 : {} ",  token.toString());
        assertThat(token).contains(accessToken.replace("Bearer ", ""));
    }

    @DisplayName("AccessToken에서 Email 추출 테스트 성공")
    @Test
    void extractEmail() {
        //given
        String email = "test@test.com";
        String accessToken = jwtService.createAccessToken(email);

        //when
        Optional<String> userEmail = jwtService.extractEmail(accessToken);

        //then
        assertTrue(userEmail.isPresent(), "이메일 추출 실패: 토큰이 빈 값을 반환함");
        assertEquals(email, userEmail.get(), "추출된 이메일이 기대하는 값과 다름");
    }

    @DisplayName("AccessToken에서 Email 추출 테스트 실패 (유효하지 않은 토큰)")
    @Test
    void failExtractEmail() {
        //given
        String wrongAccessToken = "qweasdzxc";

        //when
        Optional<String> extractedEmail = jwtService.extractEmail(wrongAccessToken);

        //then
        assertThat(extractedEmail).isEmpty();
    }

    @DisplayName("RefreshToken DB 저장(업데이트) 테스트")
    @Test
    void updateRefreshToken() throws Exception{
        //given
        String token = jwtService.createRefreshToken();

        //when
        jwtService.updateRefreshToken(email, token);

        //then
        Optional<User> user = userRepository.findByEmailValue(email);
        String userRepreshToken = user.get().getRefreshToken();
        assertThat(token).isEqualTo(userRepreshToken);

    }

    @DisplayName("RefreshToken DB 저장(업데이트) 테스트 실패 (비회원)")
    @Test
    void failUpdateRefreshToken() {
        //given
        String wrongemail = "qweasdzxc";
        String token = jwtService.createRefreshToken();

        //then
        assertThrows(Exception.class, () -> jwtService.updateRefreshToken(wrongemail, token));
    }

    @DisplayName("토큰 검증 테스트 성공")
    @Test
    void isTokenValid() {
        //given
        Optional<User> userOpt = userService.findEmail("test");
        User user = userOpt.get();
        String userAccessToken = jwtService.createAccessToken(user.getEmail().getValue());

        //when
        boolean accessTokenValid = jwtService.isTokenValid(userAccessToken);
        boolean refreshTokenValid = jwtService.isTokenValid(user.getRefreshToken());

        //then
        assertThat(accessTokenValid).isTrue();
        assertThat(refreshTokenValid).isTrue();
    }

    @DisplayName("토큰 검증 테스트 실패 (유효하지 않은 토큰)")
    @Test
    void failIsTokenValid() {
        //given
        String wrongToken = "qweasdzxc";

        //when
        boolean tokenValid = jwtService.isTokenValid(wrongToken);

        //then
        assertThat(tokenValid).isFalse();
    }

}
