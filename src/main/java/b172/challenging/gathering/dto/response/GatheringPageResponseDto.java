package b172.challenging.gathering.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record GatheringPageResponseDto(
	List<GatheringResponseDto> gatheringList,
	int pageNo,
	int pageSize,
	long totalElements,
	int totalPages,
	boolean lastPage
) {
}
