package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.GatheringSavingLog;
import lombok.Builder;

@Builder
public record GatheringSavingLogResponseDto(
        GatheringSavingLog gatheringSavingLog
) { }