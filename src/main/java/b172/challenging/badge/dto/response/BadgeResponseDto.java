package b172.challenging.badge.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BadgeResponseDto(
        Long id,
        String name,
        String description,
        String imageUrl,
        LocalDateTime createdAt
) {
}
