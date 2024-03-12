package b172.challenging.activitylog.dto;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.member.dto.MemberDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ActivityLogDto{
    private final Long id;
    private final MemberDto member;
    private final ActivityCategory activityCategory;
    private final ActivityType activityType;
    private final String description;
    private final LocalDateTime createdAt;
    public ActivityLogDto(ActivityLog activityLog) {
        this.id = activityLog.getId();
        this.member = new MemberDto(activityLog.getMember());
        this.activityCategory = activityLog.getActivityCategory();
        this.activityType = activityLog.getActivityType();
        this.description = activityLog.getDescription();
        this.createdAt = activityLog.getCreatedAt();
    }
}
