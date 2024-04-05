package b172.challenging.admin.controller;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.dto.ActivityLogResponseDto;
import b172.challenging.activitylog.service.ActivityService;
import b172.challenging.admin.dto.MemberSearchRequestDto;
import b172.challenging.badge.service.BadgeService;
import b172.challenging.gathering.service.GatheringService;
import b172.challenging.member.domain.Member;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.dto.response.MemberProfileResponseDto;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.member.service.MemberService;
import b172.challenging.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Admin 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/member")
@Controller
public class AdminController {

    private final MemberService memberService;
    private final WalletService walletService;
    private final GatheringService gatheringService;
    private final BadgeService badgeService;
    private final ActivityService activityService;
    @GetMapping
    public String adminPage(Model model,
                            @PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                            MemberSearchRequestDto memberSearchRequestDto) {
        pageable = pageable == null
                ? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
                : pageable;

        PageResponse<MemberProfileResponseDto> members = memberService.findAllMember(memberSearchRequestDto, pageable);

        model.addAttribute("title", "회원 관리 페이지");
        model.addAttribute("members",members);
        model.addAttribute("condition",memberSearchRequestDto);

        return "admin-page";
    }

    @GetMapping("/{memberId}")
    public String memberPage(Model model, @PathVariable Long memberId) {
        Member member = memberService.findMemberById(memberId);
        String myHome = walletService.findMyWallet(memberId).myHome().name();
        int badgeCount = badgeService.countBadgeByMemberId(memberId);

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        PageResponse<ActivityLogResponseDto> activityLogs = activityService.findActivityLog(memberId,null,pageable);
        List<ActivityLogResponseDto> activityLogResponseDtoList = activityLogs.list();

        model.addAttribute("title_member", "기본 정보");
        model.addAttribute("title_gathering", "모임 현황");
        model.addAttribute("title_etc", "기타");
        model.addAttribute("title_activityLog", "이력");
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        model.addAttribute("statics", gatheringService.gatheringStatistics(memberId));
        model.addAttribute("myHome", myHome);
        model.addAttribute("badgeCount", badgeCount);
        model.addAttribute("activityLogs", activityLogResponseDtoList);

        return "member/member-page";
    }

    @GetMapping("/{id}/activity-log")
    public String memberActivityPage(Model model, @PathVariable Long id,
                                     @PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam(required = false) ActivityCategory activityCategory) {
        pageable = pageable == null
                ? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
                : pageable;

        PageResponse<ActivityLogResponseDto> activityLogs = activityService.findActivityLog(id, activityCategory, pageable);

        model.addAttribute("member_id", id);
        model.addAttribute("activity_category", ActivityCategory.values());
        model.addAttribute("activity_curr_category", activityCategory);
        model.addAttribute("title_activity_log", "회원 이력");
        model.addAttribute("activityLogs", activityLogs.list());
        model.addAttribute("pageNum", activityLogs.number());
        model.addAttribute("totalPage", activityLogs.totalPages());
        model.addAttribute("totalElements", activityLogs.totalElements());

        return "member/member-activity-log-page";
    }

    @GetMapping("/{id}/profile")
    public String memberProfilePage(Model model, @PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        model.addAttribute("title_modify_member", "회원 정보 수정");
        model.addAttribute("member_id", id);
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        return "member/member-profile-page";
    }

    @PostMapping("/{id}/profile")
    public String memberProfileUpdate(Model model, @PathVariable Long id,
                                      MemberProfileUpdateRequestDto memberProfileUpdateRequestDto) {
        memberService.updateMemberProfile(id, memberProfileUpdateRequestDto);
        Member member = memberService.findMemberById(id);
        model.addAttribute("title_modify_member", "회원 정보 수정");
        model.addAttribute("member_id", id);
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        return "member/member-profile-page";
    }
}
