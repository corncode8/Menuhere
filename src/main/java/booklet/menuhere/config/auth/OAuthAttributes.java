package booklet.menuhere.config.auth;

import booklet.menuhere.config.auth.userinfo.GithubOAuth2UserInfo;
import booklet.menuhere.config.auth.userinfo.GoogleOAuth2UserInfo;
import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.SocialType;
import booklet.menuhere.domain.User.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // Oauth2 로그인 진행시 키가 되는 필드값, PK와 같은 의미임
    private OAuth2UserInfo oAuth2UserInfo; // 소셜 타입별 로그인 유저 정보 (닉네임, 이메일 등)

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oauth2UserInfo;
    }
    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 소셜별 of 메소드(ofGoogle, ofGithub)들은 각각 소셜 로그인 API에서 제공하는
     * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
     */
    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if (socialType == SocialType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return ofGithub(userNameAttributeName, attributes);
    }
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }
    public static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GithubOAuth2UserInfo(attributes))
                .build();
    }
    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname 을 가져와서 build
     * email에는 UUID로 중복 없는 랜덤 값 생성
     * role은 GUEST로 설정
     */
    public User toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .email(UUID.randomUUID() + "@socialUser.com")
                .username(oauth2UserInfo.getNickname())
                .role(Role.GUEST)
                .build();
    }

}
