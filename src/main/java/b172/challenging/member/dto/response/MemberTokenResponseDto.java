package b172.challenging.member.dto.response;

import b172.challenging.member.domain.Role;
import lombok.Builder;

@Builder
public record MemberTokenResponseDto(
    Long memberId,
    String nickname,
    Role role,
    String oauthId,
    String accessToken,
    String refreshToken
) { }