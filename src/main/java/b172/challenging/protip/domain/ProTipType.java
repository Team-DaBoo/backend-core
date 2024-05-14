package b172.challenging.protip.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProTipType {
	YOUTUBE("유튜브"),
	BLOG("블로그"),
	APP("앱");

	private final String key;

}
