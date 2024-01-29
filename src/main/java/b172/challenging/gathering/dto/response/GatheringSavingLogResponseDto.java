package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.GatheringMember;
import lombok.Builder;

import java.util.List;

@Builder
public record GatheringSavingLogResponseDto(
        List<GatheringMember>  gatheringMemberList
) { }
