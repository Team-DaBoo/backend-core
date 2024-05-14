package b172.challenging.ranking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.ranking.dto.response.RankingResponseDto;
import b172.challenging.ranking.repository.RankingCustomRepository;

@Service
@RequiredArgsConstructor
public class RankingService {
	private final RankingCustomRepository rankingCustomRepository;
	private final RedisTemplate<String, RankingResponseDto> redisTemplate;

	public Page<RankingResponseDto> getTotalRanking(String sort, Pageable page) {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String key = "ranking:" + sort + ":" + today.format(formatter);

		ZSetOperations<String, RankingResponseDto> rankingIdZSetOperations = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<RankingResponseDto>> typedTuples = rankingIdZSetOperations.reverseRangeWithScores(
			key, 0, 30);
		Page<RankingResponseDto> rankingPage;
		if (typedTuples == null || typedTuples.isEmpty()) {
			rankingPage = rankingCustomRepository.findTotalRanking(sort, page);
			int maxAmount = Integer.MAX_VALUE;
			long currRank = 0L;
			int eqRank = 1;
			for (RankingResponseDto rankingResponseDto : rankingPage.getContent()) {
				if (maxAmount == rankingResponseDto.totalAmount()) {
					eqRank++;
				} else {
					currRank += eqRank;
					eqRank = 1;
				}
				rankingResponseDto = RankingResponseDto.builder()
					.rank(currRank)
					.memberId(rankingResponseDto.memberId())
					.nickname(rankingResponseDto.nickname())
					.totalAmount(rankingResponseDto.totalAmount())
					.homeLevel(rankingResponseDto.homeLevel())
					.build();
				rankingIdZSetOperations.add(key, rankingResponseDto, rankingResponseDto.totalAmount());
			}
			return rankingPage;
		}

		List<RankingResponseDto> rankingResponseDtoList = rankingIdZSetOperations.reverseRangeWithScores(key, 0, 30)
			.stream().map(t -> RankingResponseDto.builder()
				.rank(t.getValue().rank())
				.memberId(t.getValue().memberId())
				.nickname(t.getValue().nickname())
				.totalAmount(t.getValue().totalAmount())
				.homeLevel(t.getValue().homeLevel())
				.build()
			).toList();

		return new PageImpl<>(rankingResponseDtoList, page, rankingResponseDtoList.size());
	}
}
