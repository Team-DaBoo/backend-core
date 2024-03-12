package b172.challenging.activitylog.domain;

import static b172.challenging.activitylog.domain.ActivityCategory.*;

public enum ActivityType {

    // 중분류
    CERTIFICATE_MONEY("금액 인증", CERTIFICATE),
    REWARD_POINT("포인트 지급", CERTIFICATE),


    GET_MATERIAL("자재 구입",MYHOME),
    USE_MATERIAL("자재 사용",MYHOME),

    GET_BADGE("배지 획득",BADGE),
    LOSE_BADGE("배지 상실",BADGE),


    ;

    private final String description;
    private final ActivityCategory parentCategory;

    ActivityType(String description, ActivityCategory parentCategory) {
        this.description = description;
        this.parentCategory = parentCategory;
    }

    public String getDescription() {
        return description;
    }

    public ActivityCategory getParentCategory() {
        return parentCategory;
    }
}
