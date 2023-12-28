package b172.challenging.gathering.dto;

import b172.challenging.gathering.domain.GatheringMember;
import lombok.Builder;

import java.util.List;

@Builder
public record GatheringMemberPageResponseDto(
        List<GatheringMember> gatheringMembers,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {

}
