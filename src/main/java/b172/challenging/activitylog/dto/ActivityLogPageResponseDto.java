package b172.challenging.activitylog.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record ActivityLogPageResponseDto(

        List<ActivityLogDto> activityLogResponseDto,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) {

}
