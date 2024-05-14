package b172.challenging.gathering.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringMember;

@Builder
public record OngoingGatheringResponseDto(
	String title,
	String description,
	AppTechPlatform appTechPlatform,
	String gatheringImage,
	LocalDateTime startDate,
	LocalDateTime endDate,
	int workingDays,
	Long goalAmount,
	List<GatheringMember> gatheringMembers
) {
}
