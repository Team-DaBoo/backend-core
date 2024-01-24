package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.member.dto.MemberDto;
import lombok.Getter;

@Getter
public class GatheringPageDto {
    private Long id;
    private MemberDto ownerMember;
    private AppTechPlatform platform;
    private String gatheringImageUrl;
    private String title;
    private GatheringStatus gatheringStatus;
    private int remainPeopleNum;
    public GatheringPageDto(Gathering gathering){
        this.id = gathering.getId();
        this.ownerMember = new MemberDto(gathering.getOwnerMember());
        this.platform = gathering.getPlatform();
        this.gatheringImageUrl = gathering.getGatheringImageUrl();
        this.title = gathering.getTitle();
        this.gatheringStatus = gathering.getStatus();
        this.remainPeopleNum = gathering.getPeopleNum() - gathering.getParticipantsNum();
    }
}
