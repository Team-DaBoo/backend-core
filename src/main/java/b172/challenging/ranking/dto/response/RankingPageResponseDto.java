package b172.challenging.ranking.dto.response;

import b172.challenging.gathering.domain.Gathering;
import lombok.Builder;

import java.util.List;

@Builder
public record RankingPageResponseDto(
        List<RankingResponseDto> ranking,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) { }
