package b172.challenging.gathering.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppTechPlatform {
	TOSS("토스"),
	CASH_WALK("캐시워크"),
	MONIMO("모니모"),
	BALSO("발로소득");

	private final String key;
}
