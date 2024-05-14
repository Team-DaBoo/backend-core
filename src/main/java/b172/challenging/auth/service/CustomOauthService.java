package b172.challenging.auth.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.auth.oauth.OauthAttributes;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.service.MemberNicknameService;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CustomOauthService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final MemberNicknameService memberService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = service.loadUser(userRequest); //Oauth 정보

		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // Oauth 서비스 이름
		OauthProvider oauthProvider = getOauthProvider(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName(); // Oauth id

		Map<String, Object> attributes = oAuth2User.getAttributes();

		OauthAttributes extractAttributes = OauthAttributes.of(oauthProvider, userNameAttributeName, attributes);
		Member createdMember = getMember(extractAttributes, oauthProvider);
		return new CustomOauth2User(
			Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().getKey())),
			attributes,
			userNameAttributeName,
			createdMember.getId(),
			createdMember.getRole()
		);
	}

	public OauthProvider getOauthProvider(String registrationId) {
		if ("kakao".equals(registrationId)) {
			return OauthProvider.KAKAO;
		} else if ("google".equals(registrationId)) {
			return OauthProvider.GOOGLE;
		}
		throw new IllegalArgumentException("지원하지 않는 인증수단 입니다.");
	}

	@Transactional
	public Member getMember(OauthAttributes attributes, OauthProvider oauthProvider) {
		Member findMember = memberRepository
			.findByOauthProviderAndOauthId(oauthProvider, attributes.getOauth2UserInfo().getId()).orElse(null);
		if (findMember == null) {
			return saveMember(attributes, oauthProvider);
		}
		return findMember;
	}

	private Member saveMember(OauthAttributes attributes, OauthProvider oauthProvider) {
		Member createdMember = attributes.toEntity(oauthProvider, attributes.getOauth2UserInfo(), memberService);
		return memberRepository.save(createdMember);
	}
}
