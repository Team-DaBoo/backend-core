package b172.challenging.activitylog.dto;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.member.dto.MemberResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

public record ActivityLogResponseDto(
        Long id,
        MemberResponseDto member,
        MemberResponseDto modifier,

        String activityCategory,
        String activityType,
        String description,
        LocalDateTime createdAt
)

{

    public static ActivityLogResponseDto from(ActivityLog activityLog) {
        return new ActivityLogResponseDto(
                activityLog.getId(),
                MemberResponseDto.from(activityLog.getMember()),
                MemberResponseDto.from(activityLog.getModifier()),
                activityLog.getActivityCategory().getKey(),
                activityLog.getActivityType().getDescription(),
                activityLog.getDescription(),
                activityLog.getCreatedAt()
        );
    }
}
