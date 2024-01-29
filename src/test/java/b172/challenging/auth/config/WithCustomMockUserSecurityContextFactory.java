package b172.challenging.auth.config;

import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.Map;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    private final MemberRepository memberRepository;

    public WithCustomMockUserSecurityContextFactory(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        Member member = Member.builder()
                .nickname("nickname")
                .oauthProvider(OauthProvider.GOOGLE)
                .oauthId("oauthId")
                .build();
        member.setRole(Role.MEMBER);

        Member savedMember = memberRepository.save(member);


        CustomOauth2User customOauth2User = new CustomOauth2User(
                Collections.singleton(new SimpleGrantedAuthority(savedMember.getRole().getKey())),
                Map.of("memberId", savedMember.getId()),
                "memberId",
                savedMember.getId(),
                savedMember.getRole()
        );

        Authentication authentication = new OAuth2AuthenticationToken(customOauth2User, customOauth2User.getAuthorities(), "memberId");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return SecurityContextHolder.getContext();
    }
}
