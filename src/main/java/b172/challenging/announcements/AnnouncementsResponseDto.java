package b172.challenging.announcements;

import b172.challenging.member.domain.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record AnnouncementsResponseDto(
        List<Announcements> announcementsList,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last,
        Role role
) {
}

