package b172.challenging.announcements;

import b172.challenging.common.domain.UseYn;
import b172.challenging.member.dto.MemberResponseDto;

import java.time.LocalDateTime;

public record AnnouncementsResponseDto(
        Long id,
        String title,
        String content,
        MemberResponseDto registerId,
        UseYn useYn,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AnnouncementsResponseDto from(Announcements announcements){
        return new AnnouncementsResponseDto(
                announcements.getId(),
                announcements.getTitle(),
                announcements.getContent(),
                MemberResponseDto.from(announcements.getRegisterId()),
                announcements.getUseYn(),
                announcements.getCreatedAt(),
                announcements.getUpdatedAt()
        );
    }
}

