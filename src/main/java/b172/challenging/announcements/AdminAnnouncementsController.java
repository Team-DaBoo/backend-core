package b172.challenging.announcements;

import b172.challenging.member.domain.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

import java.security.Principal;

@Tag(name = "Admin Announcements", description = "Admin 공지사항 관련 API")
@RequiredArgsConstructor
@RequestMapping("/admin/announcements")
@Controller
public class AdminAnnouncementsController {

    private final AnnouncementsService announcementsService;
    @GetMapping
    public String adminAnnouncementsPage(Model model,
                                         @PageableDefault(size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
        pageable = pageable == null
                ? PageRequest.of(0, 5, Sort.Direction.DESC, "id")
                : pageable;

        AnnouncementsResponseDto announcementsResponseDto = announcementsService.findAllAnnouncements(Role.ADMIN, pageable);

        model.addAttribute("announcementsList" , announcementsResponseDto.announcementsList());
        model.addAttribute("pageNum",pageable.getPageNumber());
        model.addAttribute("totalPage",announcementsResponseDto.totalPages());
        model.addAttribute("totalElements",announcementsResponseDto.totalElements());

        return "announcements/announcements-page";
    }

    @GetMapping("/{id}")
    public String adminAnnouncementsPage(Model model,
                                  @PathVariable Long id){
        Announcements announcements = announcementsService.findAnnouncementsById(id);

        model.addAttribute("announcements" , announcements);

        return "announcements/announcements-detail-page";
    }

    @GetMapping("/create")
    public String proTipCreatePage() {
        return "announcements/announcements-create-page";
    }

    @PostMapping("/create")
    public String announcementCreatePage(AnnouncementsRequestDto announcementsRequestDto,
                                         Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        announcementsService.createAnnouncements(memberId, announcementsRequestDto);
        return "redirect:/admin/announcements";
    }

    @GetMapping("/update/{id}")
    public String announcementUpdatePage(Model model,
                                         @PathVariable Long id) {
        Announcements announcements = announcementsService.findAnnouncementsById(id);

        model.addAttribute("announcements" , announcements);

        return "announcements/announcements-update-page";
    }

    @PostMapping("/update/{id}")
    public String proTipUpdate(@PathVariable Long id,
                               AnnouncementsRequestDto announcementsRequestDto,
                               Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        announcementsService.updateAnnouncements(id, memberId, announcementsRequestDto);
        return "redirect:/admin/announcements/" + id;
    }

    @PostMapping("/delete/{id}")
    public String announcementDeletePage(@PathVariable Long id) {
        announcementsService.deleteAnnouncement(id);
        return "redirect:/admin/announcements";
    }
}
