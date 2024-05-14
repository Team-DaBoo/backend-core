package b172.challenging.admin.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.dto.request.GatheringMakeRequestDto;
import b172.challenging.gathering.dto.request.GatheringSavingLogRequestDto;
import b172.challenging.gathering.dto.response.GatheringMemberResponseDto;
import b172.challenging.gathering.dto.response.GatheringResponseDto;
import b172.challenging.gathering.service.GatheringMemberService;
import b172.challenging.gathering.service.GatheringSavingLogService;
import b172.challenging.gathering.service.GatheringService;

@Tag(name = "Admin Gathering", description = "Admin Gathering 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/gathering")
@Controller
public class AdminGatheringController {

	private final GatheringService gatheringService;
	private final GatheringMemberService gatheringMemberService;
	private final GatheringSavingLogService gatheringSavingLogService;

	@GetMapping
	public String adminGatheringPage(Model model,
		@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		GatheringSearchRequestDto gatheringSearchRequestDto) {
		PageResponse<GatheringResponseDto> gatherings = gatheringService.findAllGathering(gatheringSearchRequestDto,
			pageable);

		model.addAttribute("gatherings", gatherings);
		model.addAttribute("condition", gatheringSearchRequestDto);

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
		model.addAttribute("gathering", gatheringService.findById(gatheringId));
		model.addAttribute("gatheringMembers", gatheringMemberService.findGatheringId(gatheringId));

		return "gatherings/gathering-detail-page";
	}

	// ToDo
	@PostMapping("/{gatheringId}/edit")
	public String gatheringEdit(Long memberId,
		@PathVariable Long gatheringId,
		GatheringMakeRequestDto gatheringMakeRequestDto) {

		//		gatheringService.editGathering(memberId, gatheringId, gatheringMakeRequestDto);
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
		@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		@PathVariable Long memberId) {
		pageable = pageable == null
			? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
			: pageable;

		GatheringMemberResponseDto gatheringMember = gatheringMemberService.findSavingLog(gatheringId, memberId,
			pageable);

		model.addAttribute("gatheringSavingLogList", gatheringMember.gatheringSavingLogResponseDtoList());
		model.addAttribute("gatheringId", gatheringId);
		model.addAttribute("member", gatheringMember.member());
		model.addAttribute("amount", gatheringMember.amount());
		model.addAttribute("count", gatheringMember.count());

		model.addAttribute("pageNum", pageable.getPageNumber());
		model.addAttribute("totalPage",
			(gatheringMember.gatheringSavingLogResponseDtoListSize()) / pageable.getPageSize() + 1);
		model.addAttribute("totalElements", gatheringMember.gatheringSavingLogResponseDtoListSize());

		return "gatherings/gathering-member-detail-page";
	}

	@PostMapping("/{gatheringId}/member/{memberId}/left/{gatheringMemberId}")
	public String gatheringMemberLeft(@PathVariable Long gatheringId,
		@PathVariable Long memberId,
		@PathVariable Long gatheringMemberId) {
		gatheringService.leftGathering(memberId, gatheringMemberId);

		return "redirect:/admin/gathering/" + gatheringId;
	}

	@PostMapping("/saving-log/{gatheringSavingLogId}/edit/{memberId}")
	public String gatheringSavingLogEdit(@PathVariable Long gatheringSavingLogId,
		@PathVariable Long memberId,
		Long gatheringId,
		Long amount) {
		GatheringSavingLogRequestDto gatheringSavingLogRequestDto = new GatheringSavingLogRequestDto(amount,
			"https://phinf.pstatic.net/contact/20240117_182/1705501215205Vu0Ht_JPEG/1.jpg?type=f130_130", null);
		gatheringSavingLogService.updateGatheringSavingLog(memberId, gatheringSavingLogId,
			gatheringSavingLogRequestDto);

		return "redirect:/admin/gathering/" + gatheringId + "/member/" + memberId;

	}

	@PostMapping("/saving-log/{gatheringSavingLogId}/delete/{memberId}")
	public String gatheringSavingLogDelete(@PathVariable Long gatheringSavingLogId,
		@PathVariable Long memberId,
		Long gatheringId) {
		gatheringSavingLogService.deleteGatheringSavingLog(memberId, gatheringSavingLogId);

		return "redirect:/admin/gathering/" + gatheringId + "/member/" + memberId;
	}
}
