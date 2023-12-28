package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
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

        @Builder
        public static record AppTechPlatformDto(
                AppTechPlatform[] appTechPlatform
        ) { }
}
