package b172.challenging.auth.oauth.filter;

import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.auth.service.JwtService;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        // ACCESS_TOKEN 확인
        String accessToken = request.getHeader(jwtService.getAccessHeader()) != null
                ? request.getHeader(jwtService.getAccessHeader()).replace("Bearer ", "")
                : null;

        if(accessToken != null && jwtService.verifyToken(accessToken)){
            Long memberId = jwtService.extractMemberId(accessToken);
            String jwtCode = jwtService.extractJwtCode(accessToken);
            Member member = verifyJwtCodeAndAuthenticate(memberId, jwtCode);
            saveAuthentication(member);
            filterChain.doFilter(request, response);
            return;
        }

        // REFRESH_TOKEN 확인
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jwtService.getRefreshHeader())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if(refreshToken != null && jwtService.verifyToken(refreshToken)) {
            checkRefreshTokenAndReIssueTokens(response, refreshToken);
        }

        filterChain.doFilter(request, response);
    }

    public void checkRefreshTokenAndReIssueTokens(HttpServletResponse response, String refreshToken) throws IOException, IllegalArgumentException{
        Long memberId = jwtService.extractMemberId(refreshToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        String storedJwtCode = member.getJwtCode();
        String tokenJwtCode = jwtService.extractJwtCode(refreshToken);

        if (!tokenJwtCode.equals(storedJwtCode)) {
            throw new CustomRuntimeException(Exceptions.UNAUTHORIZED);
        }

        jwtService.sendAccessAndRefreshToken(
                response, jwtService.createAccessToken(memberId, member.getRole()), jwtService.createRefreshToken(memberId)
        );
        saveAuthentication(member);
    }

    public Member verifyJwtCodeAndAuthenticate(Long memberId, String jwtCode) {
        return memberRepository.findById(memberId)
                .filter(savedJwtCode -> savedJwtCode.getJwtCode().equals(jwtCode))
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.UNAUTHORIZED));
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
