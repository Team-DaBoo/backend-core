package b172.challenging.ranking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.ranking.dto.response.RankingResponseDto;

public interface RankingCustomRepository {

	Page<RankingResponseDto> findTotalRanking(String sort, Pageable page);
}
