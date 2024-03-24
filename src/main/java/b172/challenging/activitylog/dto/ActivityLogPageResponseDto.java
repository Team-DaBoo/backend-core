package b172.challenging.activitylog.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ActivityLogPageResponseDto(
        List<ActivityLogResponseDto> activityLogs,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) {
}
