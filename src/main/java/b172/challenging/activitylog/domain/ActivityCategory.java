package b172.challenging.activitylog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityCategory {
    CERTIFICATE("인증"),
    MYHOME("나의집"),
    BADGE("배지")

    ;

    private final String value;
}
