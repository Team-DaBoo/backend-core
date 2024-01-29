package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringMember;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

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
)
{ }