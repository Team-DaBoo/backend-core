package b172.challenging.badge.controller;

import b172.challenging.badge.dto.response.BadgeDto;
import b172.challenging.badge.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "배지 API", description = "배지 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/badge")
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("")
    @Operation(summary = "사용자 보유 배지 리스트 가져오기" , description = "사용자 보유 배지 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = BadgeDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
    })
    public ResponseEntity<List<BadgeDto>> getMyBadgeList (Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(badgeService.findMemberBadgeList(memberId));
    }

}
