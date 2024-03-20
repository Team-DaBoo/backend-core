package b172.challenging.activitylog.dto;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.member.dto.MemberResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
        return ActivityLogResponseDto.builder()
                .id(activityLog.getId())
                .member(MemberResponseDto.from(activityLog.getMember()))
                .modifier(MemberResponseDto.from(activityLog.getModifier()))
                .activityCategory(activityLog.getActivityCategory().getKey())
                .activityType(activityLog.getActivityType().getDescription())
                .description(activityLog.getDescription())
                .createdAt(activityLog.getCreatedAt())
                .build();
    }
}
