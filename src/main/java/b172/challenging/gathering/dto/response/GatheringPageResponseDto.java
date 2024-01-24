package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.dto.GatheringPageDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GatheringPageResponseDto(
        List<GatheringPageDto> gatheringPages,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean lastPage
) { }