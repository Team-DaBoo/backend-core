package b172.challenging.auth.oauth;

import java.util.Map;

public class Oauth2UserInfoGoogle extends Oauth2UserInfo {

    public Oauth2UserInfoGoogle(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }
}
