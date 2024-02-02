package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringMember;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GatheringPageResponseDto(
        List<Gathering> gatherings,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
        ) { }
