package b172.challenging.gathering.dto;

import b172.challenging.auth.domain.Member;
import lombok.Builder;

@Builder
public record GatheringMakeResponseDto (
        Long id,
        String title,
        Member owner
){ }
