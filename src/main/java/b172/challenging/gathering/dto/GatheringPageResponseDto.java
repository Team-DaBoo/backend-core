package b172.challenging.gathering.dto;

import b172.challenging.auth.domain.Member;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record GatheringPageResponseDto(
        List<Gathering> gatherings,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
        ) {

}
