package b172.challenging.badge.dto.response;

import b172.challenging.badge.domain.Badge;

import java.time.LocalDateTime;

public record BadgeResponseDto(
        Long id,
        String name,
        String description,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static BadgeResponseDto from(Badge badge) {
        return new BadgeResponseDto(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getImageUrl(),
                badge.getCreatedAt()
        );
    }
}
