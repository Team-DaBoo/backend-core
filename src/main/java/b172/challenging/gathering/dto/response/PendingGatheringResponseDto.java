package b172.challenging.gathering.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;

@Builder
public record PendingGatheringResponseDto(
	String title,
	String description,
	int remainNum,
	GatheringStatus gatheringStatus,
	AppTechPlatform appTechPlatform,
	String gatheringImage,
	LocalDateTime startDate,
	LocalDateTime endDate,
	int workingDays,
	Long goalAmount,
	Boolean isJoined
) {
}
