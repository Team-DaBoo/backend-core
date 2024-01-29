package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.dto.GatheringDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GatheringPageResponseDto(
        List<GatheringDto> gatheringPages,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) { }