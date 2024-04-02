package b172.challenging.gathering.dto.response;

import lombok.Builder;

@Builder
public record GatheringStatisticsResponseDto(
    Long onGoingCount,
    Long completedCount,
    Long ownerGatheringCount,
    Double achievementRate
){ }
