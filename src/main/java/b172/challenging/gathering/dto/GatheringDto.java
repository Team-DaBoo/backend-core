package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.member.dto.MemberDto;
import lombok.Getter;

@Getter
public class GatheringDto {
    private final Long id;
    private final MemberDto ownerMember;
    private final AppTechPlatform platform;
    private final String gatheringImageUrl;
    private final String title;
    private final GatheringStatus gatheringStatus;
    private final int remainPeopleNum;
    public GatheringDto(Gathering gathering){
        this.id = gathering.getId();
        this.ownerMember = new MemberDto(gathering.getOwnerMember());
        this.platform = gathering.getPlatform();
        this.gatheringImageUrl = gathering.getGatheringImageUrl();
        this.title = gathering.getTitle();
        this.gatheringStatus = gathering.getStatus();
        this.remainPeopleNum = gathering.getPeopleNum() - gathering.getParticipantsNum();
    }
}
