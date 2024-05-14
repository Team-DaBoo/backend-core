package b172.challenging.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex {
	MALE("남자"),
	FEMALE("여자"),
	NONE("없음");

	private final String key;
}
