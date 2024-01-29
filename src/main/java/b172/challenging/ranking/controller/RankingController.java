package b172.challenging.ranking.controller;

import b172.challenging.ranking.dto.response.RankingPageResponseDto;
import b172.challenging.ranking.service.RankingService;
import b172.challenging.wallet.dto.WalletResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "랭킹 API", description = "랭킹 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ranking")
public class RankingController {

    public final RankingService rankingService;
    @GetMapping("")
    @Operation(summary = "전체 랭킹" , description = "전체 랭킹 가져오기(매일 00시 00분 기준)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = RankingPageResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
    })
    public ResponseEntity<RankingPageResponseDto> getTotalRanking (
            @RequestParam(defaultValue = "daily") String sort,
            @PageableDefault(size = 30, direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(rankingService.getTotalRanking(sort, page));
    }
}
