package b172.challenging.gathering.dto.response;

import lombok.Builder;

@Builder
public record GatheringStatusCountResponseDto (
    Long onGoingCount,
    Long completedCount,
    Long ownerGatheringCount
    ){ }
