package b172.challenging.gathering.dto.response;

import java.time.LocalDateTime;

import b172.challenging.gathering.domain.GatheringSavingCertification;

public record GatheringSavingCertificationResponseDto(
	Long id,
	String imageUrl,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static GatheringSavingCertificationResponseDto from(
		GatheringSavingCertification gatheringSavingCertification) {
		return new GatheringSavingCertificationResponseDto(
			gatheringSavingCertification.getId(),
			gatheringSavingCertification.getImageUrl(),
			gatheringSavingCertification.getCreatedAt(),
			gatheringSavingCertification.getUpdatedAt()
		);
	}
}
