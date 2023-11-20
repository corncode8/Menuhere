package booklet.menuhere.config.auth.userinfo;

import booklet.menuhere.config.auth.OAuth2UserInfo;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return attributes.get("id").toString();
    }
    @Override
    public String getNickname() {
        return (String) attributes.get("login");
    }
    // github은 login시 id를 username에 넣어줌.

}
