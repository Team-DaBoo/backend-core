package b172.challenging.protip.dto;

import lombok.Builder;

import b172.challenging.protip.domain.ProTip;

@Builder
public record ProTipMakeResponseDto(
	ProTip proTip
) {
}
