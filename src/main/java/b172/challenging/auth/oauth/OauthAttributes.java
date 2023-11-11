package b172.challenging.auth.oauth;

import b172.challenging.auth.domain.Member;
import b172.challenging.auth.domain.OauthProvider;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OauthAttributes {

    private String nameAttributeKey;
    private Oauth2UserInfo oauth2UserInfo;

    @Builder
    public OauthAttributes(String nameAttributeKey, Oauth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OauthAttributes of(OauthProvider oauthProvider,
                                     String userNameAttributeName, Map<String, Object> attributes) {
        if (oauthProvider == OauthProvider.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OauthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OauthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new Oauth2UserInfoKakao(attributes))
                .build();
    }

    private static OauthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OauthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new Oauth2UserInfoGoogle(attributes))
                .build();
    }

    public Member toEntity(OauthProvider oauthProvider, Oauth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .oauthProvider(oauthProvider)
                .oauthId(oauth2UserInfo.getId())
                .nickname(oauth2UserInfo.getNickname())
                .build();
    }
}
