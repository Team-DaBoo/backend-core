package b172.challenging.auth.service;

import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private static final String MEMBER_ID_CLAIM = "memberId";
    private static final String CODE_CLAIM = "code";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES_KEY = "authority";

    private final MemberRepository memberRepository;


    public String createAccessToken(Long memberId) {
        Date now = new Date();
        String jwtCode = saveRandomJwtCode(memberId);
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpiration))
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
        response.setHeader(refreshHeader, refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader));
    }


    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }


    public Long extractMemberId(String token) throws Exception {
        Claim memberIdClaim = JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token)
                    .getClaim(MEMBER_ID_CLAIM);

        if (memberIdClaim.isNull()) {
            throw new Exception("사용자 정보를 찾을 수 없습니다.");
        }
        return memberIdClaim.asLong();
    }


    public String extractJwtCode(String token) throws Exception{
        Claim CodeClaim = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token)
                .getClaim(CODE_CLAIM);

        if (CodeClaim.isNull()) {
            throw new Exception("사용자 정보를 찾을 수 없습니다.");
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
                .orElseThrow(() ->  new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));
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