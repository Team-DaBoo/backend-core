package b172.challenging.admin.controller;

import b172.challenging.admin.dto.ProTipSearchRequestDto;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.member.domain.Role;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;
import b172.challenging.protip.dto.ProTipRequestDto;
import b172.challenging.protip.dto.ProTipResponseDto;
import b172.challenging.protip.service.ProTipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Admin ProTip", description = "Admin 꿀팁 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/pro-tips")
@Controller
public class AdminProTipController {

    private final ProTipService proTipService;

    @GetMapping
    public String adminProTipPage(Model model,
                                  @PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                                  ProTipSearchRequestDto proTipSearchRequestDto){

        pageable = pageable == null
                ? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
                : pageable;

        PageResponse<ProTipResponseDto> proTips =  proTipService.findAllProTip(Role.ADMIN,proTipSearchRequestDto,pageable);

        model.addAttribute("proTips" , proTips);
        model.addAttribute("condition",proTipSearchRequestDto);

        return "pro-tips/pro-tip-page";
    }

    @GetMapping("/{id}")
    public String adminProTipPage(Model model,
                                  @PathVariable Long id){
        ProTip proTip = proTipService.findProTipById(id);

        model.addAttribute("proTip" , proTip);

        return "pro-tips/pro-tip-detail-page";
    }

    @GetMapping("/create")
    public String proTipCreatePage(Model model) {
        model.addAttribute("proTipTypes", ProTipType.values());
        return "pro-tips/pro-tip-create-page";
    }

    @PostMapping("/create")
    public String proTipCreate(ProTipRequestDto proTipRequestDto,
                               Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        proTipService.createProTip(memberId, proTipRequestDto);

        return "redirect:/admin/pro-tips";
    }

    @GetMapping("/update/{id}")
    public String proTipUpdatePage(Model model,
                                   @PathVariable Long id) {
        ProTip proTip = proTipService.findProTipById(id);

        model.addAttribute("proTip" , proTip);
        model.addAttribute("proTipTypes", ProTipType.values());

        return "pro-tips/pro-tip-update-page";
    }

    @PostMapping("/update/{id}")
    public String proTipUpdate(@PathVariable Long id,
                               ProTipRequestDto proTipRequestDto,
                               Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        proTipService.postProTip(id, memberId, proTipRequestDto);
        return "redirect:/admin/pro-tips/" + id;
    }

    @PostMapping("/delete/{id}")
    public String proTipDelete(@PathVariable Long id) {
        proTipService.deleteProTip(id);
        return "redirect:/admin/pro-tips";
    }
}
