package b172.challenging.gathering.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GatheringStatus {
    PENDING("시작전"),
    ONGOING("진행중"),
    COMPLETED("완료");
    private final String key;
}
