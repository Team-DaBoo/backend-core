package b172.challenging.badge.dto.response;

import java.time.LocalDateTime;

import b172.challenging.badge.domain.Badge;

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
