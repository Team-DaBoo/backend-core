package b172.challenging.protip.controller;

import b172.challenging.common.dto.PageResponse;
import b172.challenging.member.domain.Role;
import b172.challenging.auth.oauth.CustomOauth2User;
import b172.challenging.protip.domain.ProTipType;
import b172.challenging.protip.dto.ProTipResponseDto;
import b172.challenging.protip.service.ProTipService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "꿀팁 API", description = "꿀팁 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pro-tip")
public class ProTipController {

    private final ProTipService proTipservice;

    @GetMapping(value = {"" , "/{type}"})
    @Operation(summary = "꿀팁 정보 가져오기", description = "전체, 타입 별 꿀팁 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    @Parameter(name = "type", description = "type : [YOUTUBE , BLOG, APP]")
    public ResponseEntity<PageResponse<ProTipResponseDto>> getProTip(@PathVariable(required = false) ProTipType type,
                                                                    @PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable page,
                                                                    @AuthenticationPrincipal DefaultOAuth2User oauth2User) {
        CustomOauth2User customOauth2User = (CustomOauth2User) oauth2User;
        Role role = customOauth2User.getRole();

        return type == null
                ? ResponseEntity.ok(proTipservice.findAllProTip(role, null, page))
                : ResponseEntity.ok(proTipservice.findProTipByType(role, type, page)) ;
    }
}
