package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringMemberStatus;
import b172.challenging.member.dto.MemberDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GatheringMemberDto {
    private final Long id;
    private final GatheringDto gathering;
    private final MemberDto member;
    private final GatheringMemberStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    public GatheringMemberDto(GatheringMember gatheringMember){
        this.id = gatheringMember.getId();
        this.gathering = new GatheringDto(gatheringMember.getGathering());
        this.member = new MemberDto(gatheringMember.getMember());
        this.status = gatheringMember.getStatus();
        this.createdAt = gatheringMember.getCreatedAt();
        this.updatedAt = gatheringMember.getUpdatedAt();
    }
}
