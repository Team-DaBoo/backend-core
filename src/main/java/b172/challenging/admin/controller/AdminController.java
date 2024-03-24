package b172.challenging.admin.controller;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.dto.ActivityLogResponseDto;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import b172.challenging.activitylog.service.ActivityService;
import b172.challenging.badge.repository.BadgeMemberRepository;
import b172.challenging.gathering.service.GatheringService;
import b172.challenging.member.domain.Member;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.dto.response.MemberProfileResponseDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.service.MemberService;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.repository.WalletRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Admin", description = "Admin 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/member")
@Controller
public class AdminController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final WalletRepository walletRepository;
    private final GatheringService gatheringService;
    private final BadgeMemberRepository badgeMemberRepository;
    private final ActivityLogRepository activityLogRepository;
    private final ActivityService activityService;
    @GetMapping
    public String adminPage(Model model, @PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
        pageable = pageable == null
                ? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
                : pageable;
        Page<Member> members = memberRepository.findAll(pageable);
        List<MemberProfileResponseDto> memberList = members.getContent().stream().map(MemberProfileResponseDto::from).toList();

        model.addAttribute("title", "회원 관리 페이지");
        model.addAttribute("memberList",memberList);
        model.addAttribute("pageNum",pageable.getPageNumber());
        model.addAttribute("totalElements",members.getTotalElements());
        model.addAttribute("totalPage",members.getTotalPages());
        model.addAttribute("totalElements", members.getTotalElements());

        return "admin-page";
    }

    @GetMapping("/{id}")
    public String memberPage(Model model, @PathVariable Long id) {
        Member member = memberRepository.getOrThrow(id);

        Optional<Wallet> wallet = walletRepository.findByMemberId(id);
        String myHome = null;
        if (wallet.isPresent()){
            myHome = wallet.get().getMyHome().getName();
        }

        int badgeCount = badgeMemberRepository.findByMemberId(id).size();

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<ActivityLog> activityLogs = activityLogRepository.findByMemberId(id, pageable);
        List<ActivityLogResponseDto> activityLogResponseDtoList = activityLogs.getContent().stream().map(ActivityLogResponseDto::from).toList();

        model.addAttribute("title_member", "기본 정보");
        model.addAttribute("title_gathering", "모임 현황");
        model.addAttribute("title_etc", "기타");
        model.addAttribute("title_activityLog", "이력");
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        model.addAttribute("statics", gatheringService.gatheringStatistics(id));
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

        Page<ActivityLogResponseDto> activityLogs = activityService.findActivityLog(id, activityCategory, pageable);

        model.addAttribute("member_id", id);
        model.addAttribute("activity_category", ActivityCategory.values());
        model.addAttribute("activity_curr_category", activityCategory);
        model.addAttribute("title_activity_log", "회원 이력");
        model.addAttribute("activityLogs", activityLogs.getContent());
        model.addAttribute("pageNum", activityLogs.getNumber());
        model.addAttribute("totalPage", activityLogs.getTotalPages());
        model.addAttribute("totalElements", activityLogs.getTotalElements());

        return "member/member-activity-log-page";
    }

    @GetMapping("/{id}/profile")
    public String memberProfilePage(Model model, @PathVariable Long id) {
        Member member = memberRepository.getOrThrow(id);
        model.addAttribute("title_modify_member", "회원 정보 수정");
        model.addAttribute("member_id", id);
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        return "member/member-profile-page";
    }

    @PostMapping("/{id}/profile")
    public String memberProfileUpdate(Model model, @PathVariable Long id,
                                      MemberProfileUpdateRequestDto memberProfileUpdateRequestDto,
                                      BindingResult result) {
        memberService.updateMemberProfile(id, memberProfileUpdateRequestDto);
        Member member = memberRepository.getOrThrow(id);
        model.addAttribute("title_modify_member", "회원 정보 수정");
        model.addAttribute("member_id", id);
        model.addAttribute("member", MemberProfileResponseDto.from(member));
        return "member/member-profile-page";
    }
}
