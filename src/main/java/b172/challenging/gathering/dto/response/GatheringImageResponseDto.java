package b172.challenging.gathering.dto.response;

import lombok.Builder;

import b172.challenging.gathering.domain.GatheringImage;

@Builder
public record GatheringImageResponseDto(
	Long id,
	String url
) {
	public static GatheringImageResponseDto of(GatheringImage gatheringImage) {
		return GatheringImageResponseDto.builder()
			.id(gatheringImage.getId())
			.url(gatheringImage.getUrl())
			.build();
	}

}
