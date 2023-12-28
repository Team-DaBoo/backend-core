package b172.challenging.gathering.controller;


import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.gathering.dto.request.GatheringMakeRequestDto;
import b172.challenging.gathering.dto.response.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "함께 모으기 API", description = "함께 모으기 (홈 화면) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    @GetMapping("/in-progress")
    @Operation(summary = "내 참가 현황 가져오기", description = "내가 참가하고 있는 모임을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringMemberPageResponseDto> getInProgressMyGathering (Principal principal,
                                                                                    @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page){
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.findMyGatheringInProgress(userId ,page));
    }

    @GetMapping(value = {"/{status}/{platform}", "/{status}"})
    @Operation(summary = "PlatForm 에 따른 모임 가져오기", description = "AppPlatForm 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    @Parameter(name = "status", description = "status : [PENDING] or [ONGOING, COMPLETED]", example = "TOSS")
    @Parameter(name = "platform", description = "platform : [TOSS, CASH_WORK, MONIMO, BALSO]", example = "TOSS")
    public ResponseEntity<GatheringPageResponseDto> getGathering (@PathVariable(required = false) AppTechPlatform platform,
                                                                  @PathVariable GatheringStatus status ,
                                                                  @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page){
        return platform == null
                ? ResponseEntity.ok(gatheringService.findAllGathering(status ,page))
                : ResponseEntity.ok(gatheringService.findGatheringByPlatform(platform, status, page));
    }

    @GetMapping(value = "/platform")
    @Operation(summary = "AppPlatForm 가져오기", description = "AppPlatForm 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringPageResponseDto.AppTechPlatformDto> getPlatform (){
        return ResponseEntity.ok(
                GatheringPageResponseDto.AppTechPlatformDto.builder()
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
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.makeGathering(userId,gatheringMakeRequestDto));
    }

    @GetMapping("/info/{gatheringId}")
    @Operation(summary = "모임 상세 정보", description = "모임 상세 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringStatusResponseDto> getGatheringStatus(Principal principal,
                                                                         @PathVariable Long gatheringId
    ){
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.findGatheringStatus(gatheringId, userId));
    }

    @PutMapping("/join/{gatheringId}")
    @Operation(summary = "모임 참가 하기", description = "현재 유저가 모임에 참가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인서트 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<JoinGatheringResponseDto> joinGathering(Principal principal,
                                                                  @PathVariable Long gatheringId){
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.joinGathering(userId, gatheringId));
    }

    @PostMapping("/left/{gatheringMemberId}")
    @Operation(summary = "모임 나가기", description = "현재 유저가 모임에서 나갑니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<LeftGatheringResponseDto> leftGathering(Principal principal,
                                                @PathVariable Long gatheringMemberId){
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.leftGathering(userId, gatheringMemberId));
    }

    @GetMapping("/status-count")
    @Operation(summary = "모임 현황 개수", description = "참가중, 완료, 내가 만든 모임 개수를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = GatheringStatusCountResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<GatheringStatusCountResponseDto> getGatheringStatusCount(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gatheringService.gatheringStatusCount(memberId));
    }
}
