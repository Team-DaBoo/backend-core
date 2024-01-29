package b172.challenging.ranking.repository;

import b172.challenging.ranking.dto.response.RankingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankingCustomRepository {

    Page<RankingResponseDto> findTotalRanking(String sort, Pageable page);
}
