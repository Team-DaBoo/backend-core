package b172.challenging.auth.oauth.filter;

import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.auth.service.CustomOauthService;
import b172.challenging.auth.service.JwtService;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ALLOW_PATH = "/login";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private CustomOauthService customOauthService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getRequestURI().equals(ALLOW_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::verifyToken)
                .orElse(null);
        /**
         * refresh token 전송시 access, refresh token 모두 재발급
         */
        if (refreshToken != null) {
            try {
                checkRefreshTokenAndReIssueTokens(response, refreshToken);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } return;
        }
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    public void checkRefreshTokenAndReIssueTokens(HttpServletResponse response, String refreshToken) throws Exception {
        Long memberId = jwtService.extractMemberId(refreshToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        String storedJwtCode = member.getJwtCode();

        String tokenJwtCode = jwtService.extractJwtCode(refreshToken);
        if (!tokenJwtCode.equals(storedJwtCode)) {
            throw new RuntimeException("Refresh Token이 올바르지 않습니다.");
        }
        jwtService.sendAccessAndRefreshToken(
                response, jwtService.createAccessToken(memberId, member.getRole()), jwtService.createRefreshToken(memberId)
        );
    }

    public void checkAccessTokenAndAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::verifyToken)
                .ifPresent(accessToken -> {
                    try {
                        Long memberId = jwtService.extractMemberId(accessToken);
                        String jwtCode = jwtService.extractJwtCode(accessToken);
                        Member member = verifyJwtCodeAndAuthenticate(memberId, jwtCode);
                        saveAuthentication(member);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        filterChain.doFilter(request, response);
    }

    public Member verifyJwtCodeAndAuthenticate(Long memberId, String jwtCode) {
        memberRepository.findJwtCodeById(memberId)
                .filter(savedJwtCode -> savedJwtCode.equals(jwtCode))
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.UNAUTHORIZED));
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(
                                "사용자 ID: " + memberId + "를 찾을 수 없습니다."));
    }

    public void saveAuthentication(Member member) {
        CustomOauth2User customOauth2User = new CustomOauth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                Map.of("memberId", member.getId()),
                "memberId",
                member.getId(),
                member.getRole()
        );
        Authentication authentication = new OAuth2AuthenticationToken(customOauth2User, customOauth2User.getAuthorities(), "memberId");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
