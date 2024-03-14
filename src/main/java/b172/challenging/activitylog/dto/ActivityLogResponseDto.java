package b172.challenging.activitylog.dto;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.member.dto.MemberDto;

public record ActivityLogResponseDto(
        Long id,
        MemberDto member,
        ActivityCategory activityCategory,
        ActivityType activityType,
        String description
)

{

    public static ActivityLogResponseDto from(ActivityLog activityLog) {
        return new ActivityLogResponseDto (
                activityLog.getId(),
                new MemberDto(activityLog.getMember()),
                activityLog.getActivityCategory(),
                activityLog.getActivityType(),
                activityLog.getDescription()
        );
    }
}
