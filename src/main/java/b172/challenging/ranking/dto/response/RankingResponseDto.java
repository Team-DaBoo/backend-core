package b172.challenging.ranking.dto.response;

import lombok.Builder;

@Builder
public record RankingResponseDto(
        Long rank,
        Long memberId,
        String nickname,
        Long totalAmount,
        Long homeLevel
) { }
