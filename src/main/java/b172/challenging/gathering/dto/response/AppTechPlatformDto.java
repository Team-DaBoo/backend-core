package b172.challenging.gathering.dto.response;

import lombok.Builder;

import b172.challenging.gathering.domain.AppTechPlatform;

@Builder
public record AppTechPlatformDto(
	AppTechPlatform[] appTechPlatform
) {
}
