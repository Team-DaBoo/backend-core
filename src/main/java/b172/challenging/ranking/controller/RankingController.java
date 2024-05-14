package b172.challenging.ranking.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import b172.challenging.ranking.dto.response.RankingResponseDto;
import b172.challenging.ranking.service.RankingService;

@Tag(name = "랭킹 API", description = "랭킹 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ranking")
public class RankingController {

	public final RankingService rankingService;

	@GetMapping("")
	@Operation(summary = "전체 랭킹", description = "전체 랭킹 가져오기(매일 00시 00분 기준)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
	})
	@Parameter(name = "sort", description = "정렬 기준(daily, weekly)", required = true)
	public ResponseEntity<Page<RankingResponseDto>> getTotalRanking(
		@RequestParam(defaultValue = "daily") String sort,
		@PageableDefault(size = 30, direction = Sort.Direction.DESC) Pageable page) {
		return ResponseEntity.ok(rankingService.getTotalRanking(sort, page));
	}
}
