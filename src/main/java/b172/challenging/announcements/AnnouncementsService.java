package b172.challenging.announcements;

import b172.challenging.common.domain.UseYn;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.common.dto.SearchRequestDto;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementsService {

    private final AnnouncementsRepository announcementsRepository;
    private final MemberRepository memberRepository;

    public PageResponse<AnnouncementsResponseDto> findAllAnnouncements(Role role, SearchRequestDto searchRequestDto, Pageable pageable) {
        Page<Announcements> announcementsPage =
                role == Role.ADMIN
                        ? announcementsRepository.searchByCriteria(searchRequestDto, pageable)
                        : announcementsRepository.findByUseYnIs(UseYn.Y, pageable);

        return PageResponse.from(announcementsPage.map(AnnouncementsResponseDto::from));
    }

    public Announcements findAnnouncementsById(Long id) {
        return announcementsRepository.findById(id).orElseThrow();
    }

    public void createAnnouncements(Long memberId, AnnouncementsRequestDto announcementsRequestDto) {
        Member member = memberRepository.getOrThrow(memberId);

        Announcements announcements = Announcements.builder()
                .title(announcementsRequestDto.title())
                .content(announcementsRequestDto.content())
                .registerId(member)
                .useYn(UseYn.Y)
                .build();
        announcementsRepository.save(announcements);
    }

    @Transactional
    public void updateAnnouncements(Long id, Long memberId, AnnouncementsRequestDto announcementsRequestDto) {
        Member member = memberRepository.getOrThrow(memberId);

        Announcements announcements = announcementsRepository.findById(id).orElseThrow();
        announcements.setContent(member, announcementsRequestDto);
        announcementsRepository.save(announcements);
    }

    public void deleteAnnouncement(Long id) {
        announcementsRepository.deleteById(id);
    }
}
