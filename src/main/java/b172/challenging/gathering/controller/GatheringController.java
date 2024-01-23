package b172.challenging.gathering.controller;


import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.gathering.dto.response.GatheringSavingLogCertificateResponseDto;
import b172.challenging.gathering.dto.response.GatheringSavingLogResponseDto;
import b172.challenging.gathering.dto.response.OngoingGatheringResponseDto;
import b172.challenging.gathering.dto.response.PendingGatheringResponseDto;
import b172.challenging.gathering.dto.request.GatheringMakeRequestDto;
import b172.challenging.gathering.dto.request.GatheringSavingLogRequestDto;
import b172.challenging.gathering.dto.response.*;
import b172.challenging.gathering.service.GatheringSavingLogService;
import b172.challenging.gathering.service.GatheringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "함께 모으기 API", description = "함께 모으기 (홈 화면) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/gathering")
public class GatheringController {

    private final GatheringService gatheringService;
    private final GatheringSavingLogService gatheringSavingLogService;

    @GetMapping("/in-progress")
    @Operation(summary = "내 참가 현황 가져오기", description = "내가 참가하고 있는 모임을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringPageResponseDto> getInProgressMyGathering (Principal principal,
                                                                                    @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.findMyGatheringInProgress(memberId ,page));
    }

    @GetMapping(value = {"/{status}/{platform}", "/{status}"})
    @Operation(summary = "PlatForm 에 따른 모임 가져오기", description = "AppPlatForm 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    @Parameter(name = "status", description = "status : [PENDING] or [ONGOING, COMPLETED]", example = "TOSS")
    @Parameter(name = "platform", description = "platform : [TOSS, CASH_WORK, MONIMO, BALSO]", example = "TOSS")
    public ResponseEntity<GatheringPageResponseDto> getGathering(@PathVariable(required = false) AppTechPlatform platform,
                                                                 @PathVariable GatheringStatus status,
                                                                 @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page) {
        return platform == null
                ? ResponseEntity.ok(gatheringService.findAllGathering(status, page))
                : ResponseEntity.ok(gatheringService.findGatheringByPlatform(platform, status, page));
    }

    @GetMapping(value = "/platform")
    @Operation(summary = "AppPlatForm 가져오기", description = "AppPlatForm 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<AppTechPlatformDto> getPlatform() {
        return ResponseEntity.ok(
                AppTechPlatformDto.builder()
                        .appTechPlatform(AppTechPlatform.values())
                        .build()
        );
    }

    @PostMapping("")
    @Operation(summary = "모임 만들기", description = "새로운 모임을 만듭니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringMakeResponseDto> makeGathering(Principal principal,
                                                                  @RequestBody @Valid GatheringMakeRequestDto gatheringMakeRequestDto){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.makeGathering(memberId,gatheringMakeRequestDto));
    }

    @GetMapping("/info/pending/{gatheringId}")
    @Operation(summary = "모임 상세 정보", description = "모임 상세 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<PendingGatheringResponseDto> getPendingGathering(Principal principal,
                                                                           @PathVariable Long gatheringId
    ){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.findPendingGathering(gatheringId, memberId));
    }
    @GetMapping("/info/ongoing/{gatheringId}")
    @Operation(summary = "모임 상세 정보", description = "모임 상세 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<OngoingGatheringResponseDto> getOngoingGathering(@PathVariable Long gatheringId){
        return ResponseEntity.ok(gatheringService.findOngoingGathering(gatheringId));
    }

    @PutMapping("/join/{gatheringId}")
    @Operation(summary = "모임 참가 하기", description = "현재 유저가 모임에 참가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인서트 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<JoinGatheringResponseDto> joinGathering(Principal principal,
                                                                  @PathVariable Long gatheringId){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.joinGathering(memberId, gatheringId));
    }

    @PostMapping("/left/{gatheringMemberId}")
    @Operation(summary = "모임 나가기", description = "현재 유저가 모임에서 나갑니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<LeftGatheringResponseDto> leftGathering(Principal principal,
                                                                  @PathVariable Long gatheringMemberId){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.leftGathering(memberId, gatheringMemberId));
    }

    @GetMapping("/saving-log/{gatheringId}")
    @Operation(summary = "인증 현황", description = "인증된 내역을 가지고 옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringSavingLogResponseDto> getGatheringSavingLog(@PathVariable Long gatheringId) {
        return ResponseEntity.ok(gatheringSavingLogService.findGatheringSavingLog(gatheringId));
    }

    @PutMapping(value = "/saving-log/{gatheringMemberId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "인증 하기", description = "오늘 날짜로 인증을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인서트 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringSavingLogCertificateResponseDto> putGatheringSavingLog(@PathVariable Long gatheringMemberId,
                                                                                          @RequestBody @Valid GatheringSavingLogRequestDto gatheringSavingLogRequestDto,
                                                                                          Principal principal){
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringSavingLogService.saveGatheringSavingLog(memberId, gatheringMemberId, gatheringSavingLogRequestDto));
    }

    @PostMapping("/saving-log/{savingLogId}")
    @Operation(summary = "인증 수정 하기", description = "이전 인증을 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringSavingLogCertificateResponseDto> modifyGatheringSavingLog(@PathVariable Long savingLogId,
                                                                                                                             @RequestBody @Valid GatheringSavingLogRequestDto gatheringSavingLogRequestDto,
                                                                                                                             Principal principal){

        Long memberId = Long.parseLong(principal.getName());

        return ResponseEntity.ok(gatheringSavingLogService.updateGatheringSavingLog(memberId, savingLogId, gatheringSavingLogRequestDto));
    }

    @GetMapping("/statistics")
    @Operation(summary = "모임 현황 개수", description = "참가중, 완료, 내가 만든 모임 개수, 달성률을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = GatheringStatisticsResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringStatisticsResponseDto> getGatheringStatistics(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.gatheringStatistics(memberId));
    }
}
