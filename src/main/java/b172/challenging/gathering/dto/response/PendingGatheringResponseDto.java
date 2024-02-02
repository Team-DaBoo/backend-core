package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PendingGatheringResponseDto(
        String title,
        int remainNum,
        GatheringStatus gatheringStatus,
        AppTechPlatform appTechPlatform,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int workingDays,
        Long goalAmount,
        Boolean isJoined
) { }