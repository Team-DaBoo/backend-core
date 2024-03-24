package b172.challenging.gathering.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GatheringPageResponseDto(
        List<GatheringResponseDto> gatheringList,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) { }
