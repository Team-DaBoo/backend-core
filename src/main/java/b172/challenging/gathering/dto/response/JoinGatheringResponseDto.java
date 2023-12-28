package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.GatheringMember;
import lombok.Builder;

@Builder
public record JoinGatheringResponseDto (
        GatheringMember gatheringMember
){ }
