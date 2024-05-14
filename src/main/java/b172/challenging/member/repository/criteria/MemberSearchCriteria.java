package b172.challenging.member.repository.criteria;

import static b172.challenging.common.util.ChallengingUtils.*;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import b172.challenging.member.domain.Sex;

@Getter
@Setter
@NoArgsConstructor
public class MemberSearchCriteria {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Sex sex;
	private boolean isLeaved;
	private String searchType;
	private String keyword;

	public void setStartDate(String startDate) {
		this.startDate = parseStartStringToLocalDateTime(startDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = parseEndStringToLocalDateTime(endDate);
	}
}
