package b172.challenging.admin.controller;

import java.security.Principal;

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

import b172.challenging.common.dto.PageResponse;
import b172.challenging.common.dto.SearchRequestDto;
import b172.challenging.member.domain.Role;
import b172.challenging.qna.QnA;
import b172.challenging.qna.QnARequestDto;
import b172.challenging.qna.QnAResponseDto;
import b172.challenging.qna.QnAService;

@Tag(name = "Admin QnA", description = "Admin Q&A 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/qna")
@Controller
public class AdminQnAController {
	private final QnAService qnAService;

	@GetMapping
	public String adminQnAPage(Model model,
		@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		SearchRequestDto searchRequestDto) {
		pageable = pageable == null
			? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
			: pageable;

		PageResponse<QnAResponseDto> qnAs = qnAService.findAllQnA(Role.ADMIN, searchRequestDto, pageable);

		model.addAttribute("qnAs", qnAs);
		model.addAttribute("condition", searchRequestDto);

		return "qna/qna-page";
	}

	@GetMapping("/{id}")
	public String adminQnAPage(Model model,
		@PathVariable Long id) {
		QnA qna = qnAService.findQnAById(id);

		model.addAttribute("qna", qna);

		return "qna/qna-detail-page";
	}

	@GetMapping("/create")
	public String proTipCreatePage() {
		return "qna/qna-create-page";
	}

	@PostMapping("/create")
	public String announcementCreatePage(QnARequestDto qnARequestDto,
		Principal principal) {
		Long memberId = Long.parseLong(principal.getName());
		qnAService.createQnA(memberId, qnARequestDto);
		return "redirect:/admin/qna";
	}

	@GetMapping("/update/{id}")
	public String announcementUpdatePage(Model model,
		@PathVariable Long id) {
		QnA qna = qnAService.findQnAById(id);

		model.addAttribute("qna", qna);

		return "qna/qna-update-page";
	}

	@PostMapping("/update/{id}")
	public String proTipUpdate(@PathVariable Long id,
		QnARequestDto qnARequestDto,
		Principal principal) {
		Long memberId = Long.parseLong(principal.getName());

		qnAService.updateQnA(id, memberId, qnARequestDto);
		return "redirect:/admin/qna/" + id;
	}

	@PostMapping("/delete/{id}")
	public String announcementDeletePage(@PathVariable Long id) {
		qnAService.deleteQnA(id);
		return "redirect:/admin/qna";
	}

}
