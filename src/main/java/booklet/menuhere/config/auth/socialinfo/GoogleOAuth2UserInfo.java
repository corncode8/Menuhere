package booklet.menuhere.config.auth.socialinfo;

import booklet.menuhere.config.auth.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }
    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

}
