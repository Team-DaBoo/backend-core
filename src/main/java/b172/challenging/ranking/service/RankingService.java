package b172.challenging.ranking.service;

import b172.challenging.ranking.dto.response.RankingPageResponseDto;
import b172.challenging.ranking.dto.response.RankingResponseDto;
import b172.challenging.ranking.repository.RankingCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingCustomRepository rankingCustomRepository;

    public RankingPageResponseDto getTotalRanking(String sort, Pageable page) {
        Page<RankingResponseDto> rankingPage = rankingCustomRepository.findTotalRanking(sort, page);

        return RankingPageResponseDto.builder()
                .ranking(rankingPage.getContent())
                .pageNo(rankingPage.getNumber())
                .pageSize(rankingPage.getSize())
                .totalElements(rankingPage.getTotalElements())
                .totalPages(rankingPage.getTotalPages())
                .last(rankingPage.isLast())
                .build();
    }
}