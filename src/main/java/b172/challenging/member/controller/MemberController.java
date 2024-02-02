package b172.challenging.member.controller;

import b172.challenging.member.domain.Member;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.dto.response.MemberProfileResponseDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.service.MemberService;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Tag(name = "Member", description = "Member 관련 API")
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@RestController
public class MemberController {

    public final MemberService memberService;
    public final MemberRepository memberRepository;



    @GetMapping("/profile")
    @Operation(summary = "사용자 정보", description = "토큰 정보에 해당하는 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberProfileResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));
        return ResponseEntity.ok(MemberProfileResponseDto.of(member));
    }

    @PutMapping("/profile")
    @Operation(summary = "사용자 정보 수정", description = "토큰 정보에 해당하는 사용자 정보(닉네임)를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MemberProfileResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
    })
    public ResponseEntity<MemberProfileResponseDto> updateMemberProfile(
            Principal principal,
            @RequestBody @Valid MemberProfileUpdateRequestDto memberProfileUpdateRequestDto) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = memberService.updateMemberProfile(memberId, memberProfileUpdateRequestDto);
        return ResponseEntity.ok(MemberProfileResponseDto.of(member));
    }
}
