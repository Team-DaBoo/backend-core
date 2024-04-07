package b172.challenging.admin.dto;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;

import java.time.LocalDateTime;

import static b172.challenging.common.util.ChallengingUtils.parseLocalDateTimeToString;

public record GatheringSearchRequestDto(
        String startDate,
        String endDate,
        AppTechPlatform appTechPlatform,
        GatheringStatus gatheringStatus,
        String searchCriteria,
        String searchQuery
) {
    public GatheringSearchRequestDto {
        startDate = (startDate == null) ? parseLocalDateTimeToString(LocalDateTime.now().minusDays(7)) : startDate;
        endDate = (endDate == null) ?  parseLocalDateTimeToString(LocalDateTime.now()) : endDate;
    }
}