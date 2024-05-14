package b172.challenging.gathering.dto.response;

import lombok.Builder;

import b172.challenging.member.domain.Member;

@Builder
public record GatheringMakeResponseDto(
	Long id,
	String title,
	Member owner
) {
}
