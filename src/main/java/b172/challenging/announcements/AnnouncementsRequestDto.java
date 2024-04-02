package b172.challenging.announcements;

import b172.challenging.common.domain.UseYn;

public record AnnouncementsRequestDto(
        String title,
        String content,
        UseYn useYn
) {
}
