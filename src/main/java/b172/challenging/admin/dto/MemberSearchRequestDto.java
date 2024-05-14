package b172.challenging.admin.dto;

import static b172.challenging.common.util.ChallengingUtils.*;

import java.time.LocalDateTime;

import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.domain.Sex;

public record MemberSearchRequestDto(
	String startDate,
	String endDate,
	Sex sex,
	Boolean isLeaved,
	OauthProvider oauthProvider,
	String searchCriteria,
	String searchQuery
) {
	public MemberSearchRequestDto {
		startDate = (startDate == null) ? parseLocalDateTimeToString(LocalDateTime.now().minusDays(7)) : startDate;
		endDate = (endDate == null) ? parseLocalDateTimeToString(LocalDateTime.now()) : endDate;
	}
}
