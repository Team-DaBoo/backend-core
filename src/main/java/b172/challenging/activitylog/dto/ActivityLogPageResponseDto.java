package b172.challenging.activitylog.dto;

import java.util.List;

import lombok.Builder;

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
