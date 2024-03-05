package b172.challenging.auth.service;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Value("${spring.jwt.access.expiration-in-ms}")
    private Long accessTokenExpiration;

    @Value("${spring.jwt.refresh.expiration-in-ms}")
    private Long refreshTokenExpiration;

    @Value("${spring.jwt.access.header}")
    private String accessHeader;

    @Value("${spring.jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ROLE_CLAIM = "role";
    private static final String MEMBER_ID_CLAIM = "memberId";
    private static final String CODE_CLAIM = "code";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES_KEY = "authority";
    private final MemberRepository memberRepository;


    public String createAccessToken(Long memberId, Role role) {
        Date now = new Date();
        String jwtCode = saveRandomJwtCode(memberId);
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpiration))
                .withClaim(ROLE_CLAIM, String.valueOf(role))
                .withClaim(MEMBER_ID_CLAIM, memberId)
                .withClaim(CODE_CLAIM, jwtCode)
                .sign(Algorithm.HMAC512(secretKey));
    }


    public String createRefreshToken(Long memberId) {
        Date now = new Date();

        Optional<String> jwtCodeOptional = memberRepository.findJwtCodeById(memberId);
        String jwtCode = jwtCodeOptional.orElseGet(this::generateJwtCode);

        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpiration))
                .withClaim(MEMBER_ID_CLAIM, memberId)
                .withClaim(CODE_CLAIM, jwtCode)
                .sign(Algorithm.HMAC512(secretKey));
    }


    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(accessHeader, accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
        Cookie cookie = new Cookie(refreshHeader, refreshToken);
        cookie.setMaxAge((int) (refreshTokenExpiration/1000));
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
//        response.sendRedirect("/oauth/token/KAKAO");
    }

    public Long extractMemberId(String token) throws IllegalArgumentException {
        Claim memberIdClaim = JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token)
                    .getClaim(MEMBER_ID_CLAIM);

        if (memberIdClaim.isNull()) {
            throw new CustomRuntimeException(Exceptions.UNAUTHORIZED);
        }
        return memberIdClaim.asLong();
    }


    public String extractJwtCode(String token) throws IllegalArgumentException, CustomRuntimeException{
        Claim CodeClaim = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token)
                .getClaim(CODE_CLAIM);

        if (CodeClaim.isNull()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER);
        }
        return CodeClaim.asString();
    }


    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public Member checkMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
    }

    public String saveRandomJwtCode(Long memberId) {
        String jwtCode = generateJwtCode();
        memberRepository.updateJwtCodeById(memberId, jwtCode);
        return jwtCode;
    }

    public String generateJwtCode() {
        byte[] randomBytes = new byte[20];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
