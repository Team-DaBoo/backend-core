package b172.challenging.activitylog.controller;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.dto.ActivityLogPageResponseDto;
import b172.challenging.activitylog.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "유저 활동 로그 API", description = "Member Activity Log API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/activity-log")
public class ActivityLogAdminController {

    private final ActivityService activityService;


    @GetMapping(value = {"/","/{memberId}","/{memberId}/{category}"})
    @Operation(summary = "Category 에 따른 ActivityLog 가져오기", description = "ActivityLog 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    @Parameter(name = "memberId", description = "확인할 유저 Id", example = "1234567")
    @Parameter(name = "category", description = "ActivityCategory 에 대한 정", example = "CERTIFICATE")
    public ResponseEntity<ActivityLogPageResponseDto> getActivity(Principal principal,
                                                                  @PathVariable(required = false) Long memberId,
                                                                  @PathVariable(required = false) ActivityCategory category,
                                                                  @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page) {

        return ResponseEntity.ok(activityService.findActivityLog(memberId, category, page));
    }

    @GetMapping("/category")
    @Operation(summary = "ActivityLog Category", description = "ActivityLog Category 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<List<ActivityCategory>> getActivityCategory() {
        return ResponseEntity.ok(List.of(ActivityCategory.values()));
    }

}
