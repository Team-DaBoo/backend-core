package b172.challenging.admin.controller;

import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.GatheringStatus;
import b172.challenging.gathering.dto.request.GatheringMakeRequestDto;
import b172.challenging.gathering.dto.response.GatheringPageResponseDto;
import b172.challenging.gathering.service.GatheringMemberService;
import b172.challenging.gathering.service.GatheringService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Gathering", description = "Admin Gathering 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/gathering")
@Controller
public class AdminGatheringController {

    private final GatheringService gatheringService;
    private final GatheringMemberService gatheringMemberService;
    @GetMapping
    public String adminGatheringPage(Model model,
                                     @RequestParam(required = false) GatheringStatus gatheringStatus,
                                     @RequestParam(required = false) AppTechPlatform appTechPlatform,
                                     @PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {

        GatheringPageResponseDto gatheringPageResponseDto = gatheringService.findGathering(gatheringStatus,appTechPlatform,pageable);

        model.addAttribute("gatheringList",gatheringPageResponseDto.gatheringList());
        model.addAttribute("pageNum",pageable.getPageNumber());
        model.addAttribute("totalPage",gatheringPageResponseDto.totalPages());
        model.addAttribute("totalElements",gatheringPageResponseDto.totalElements());

        return "gatherings/gathering-page";
    }

    @GetMapping("/create")
    public String gatheringCreatePage(Model model) {
        model.addAttribute("appTechPlatforms", AppTechPlatform.values());
        return "gatherings/gathering-create-page";
    }
    @PostMapping("/create")
    public String gatheringCreate(Long memberId,
                                  GatheringMakeRequestDto gatheringMakeRequestDto) {
        gatheringService.makeGathering(memberId, gatheringMakeRequestDto);

        return "gatherings/gathering-detail-page";
    }

    @GetMapping("/{gatheringId}")
    public String gatheringDetailPage(Model model,
                                      @PathVariable Long gatheringId) {
        model.addAttribute("gathering",gatheringService.findById(gatheringId));
        model.addAttribute("gatheringMembers",gatheringMemberService.findGatheringId(gatheringId));

        return "gatherings/gathering-detail-page";
    }

    @PostMapping("/{gatheringId}/edit")
    public String gatheringEdit(Long memberId,
                                @PathVariable Long gatheringId,
                                GatheringMakeRequestDto gatheringMakeRequestDto) {

//        gatheringService.editGathering(memberId, gatheringId, gatheringMakeRequestDto);
        return "gatherings/gathering-detail-page";
    }

    @PostMapping("/{gatheringId}/delete")
    public String gatheringDelete(@PathVariable Long gatheringId) {
        gatheringService.deleteGathering(gatheringId);

        return "redirect:/admin/gathering";
    }

    @GetMapping("/{gatheringId}/member/{memberId}")
    public String gatheringMemberDetailPage(Model model,
                                            @PathVariable Long gatheringId,
                                            @PathVariable Long memberId) {
//        model.addAttribute("gatheringMember",gatheringMemberService.findGatheringMember(gatheringId,memberId));
        return "gatherings/gathering-member-detail-page";
    }
}
