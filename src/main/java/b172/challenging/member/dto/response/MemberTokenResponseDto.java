package b172.challenging.member.dto.response;

import lombok.Builder;

import b172.challenging.member.domain.Role;

@Builder
public record MemberTokenResponseDto(
	Long memberId,
	String nickname,
	Role role,
	String oauthId,
	String accessToken,
	String refreshToken
) {
}
