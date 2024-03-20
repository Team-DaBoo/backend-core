package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringMemberStatus;
import b172.challenging.member.dto.MemberResponseDto;

import java.time.LocalDateTime;


public record GatheringMemberResponseDto (
    Long id,
    GatheringResponseDto gathering,
    MemberResponseDto member,
    GatheringMemberStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static GatheringMemberResponseDto from(GatheringMember gatheringMember){
        return new GatheringMemberResponseDto(
                gatheringMember.getId(),
                GatheringResponseDto.from(gatheringMember.getGathering()),
                MemberResponseDto.from(gatheringMember.getMember()),
                gatheringMember.getStatus(),
                gatheringMember.getCreatedAt(),
                gatheringMember.getUpdatedAt()
        );
    }
}
