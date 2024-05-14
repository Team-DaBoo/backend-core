package b172.challenging.gathering.dto.response;

import lombok.Builder;

import b172.challenging.gathering.domain.GatheringMember;

@Builder
public record JoinGatheringResponseDto(
	GatheringMember gatheringMember
) {
}
