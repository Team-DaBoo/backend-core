package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.AppTechPlatform;
import lombok.Builder;


@Builder
public record AppTechPlatformDto(
        AppTechPlatform[] appTechPlatform
) { }
