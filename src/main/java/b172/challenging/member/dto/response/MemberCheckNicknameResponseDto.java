package b172.challenging.member.dto.response;

import lombok.Builder;

@Builder
public record MemberCheckNicknameResponseDto(
	Boolean duplicate
) {
}
