package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.member.dto.MemberResponseDto;

import java.time.LocalDateTime;

public record GatheringResponseDto (
        Long id,
        MemberResponseDto ownerMember,
        AppTechPlatform platform,
        String gatheringImageUrl,
        String title,
        GatheringStatus gatheringStatus,
        LocalDateTime startDate,
        LocalDateTime endDate,

        Long goalAmount,

        int workingDays,
        int remainPeopleNum
) {

    public static GatheringResponseDto from(Gathering gathering){
        return new GatheringResponseDto(
                gathering.getId(),
                MemberResponseDto.from(gathering.getOwnerMember()),
                gathering.getPlatform(),
                gathering.getGatheringImageUrl(),
                gathering.getTitle(),
                gathering.getStatus(),
                gathering.getStartDate(),
                gathering.getEndDate(),
                gathering.getGoalAmount(),
                gathering.getWorkingDays(),
                gathering.getPeopleNum() - gathering.getParticipantsNum()
        );
    }
}
