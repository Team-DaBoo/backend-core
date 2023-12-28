package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;
import lombok.Builder;

@Builder
public record GatheringStatusResponseDto(
        String title,
        int remainNum,
        GatheringStatus gatheringStatus,
        AppTechPlatform appTechPlatform,
        int workingDays,
        Long goalAmount,
        Boolean isExist
) { }
