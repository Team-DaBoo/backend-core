package b172.challenging.protip.dto;

import java.time.LocalDateTime;

import b172.challenging.common.domain.UseYn;
import b172.challenging.member.dto.MemberResponseDto;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;

public record ProTipResponseDto(
	Long id,
	ProTipType proTipType,
	String title,
	String content,
	String imgUrl,
	String appLinkUrl,
	MemberResponseDto registerId,
	UseYn useYn,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static ProTipResponseDto from(ProTip proTip) {
		return new ProTipResponseDto(
			proTip.getId(),
			proTip.getProTipType(),
			proTip.getTitle(),
			proTip.getContent(),
			proTip.getImgUrl(),
			proTip.getAppLinkUrl(),
			MemberResponseDto.from(proTip.getRegisterId()),
			proTip.getUseYn(),
			proTip.getCreatedAt(),
			proTip.getUpdatedAt()
		);
	}
}
