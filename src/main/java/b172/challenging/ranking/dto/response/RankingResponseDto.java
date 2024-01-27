package b172.challenging.ranking.dto.response;

import lombok.Builder;

@Builder
public record RankingResponseDto(
        Long totalAmount,
        Long memberId,
        String nickname,
        String homeImageUrl
) { }