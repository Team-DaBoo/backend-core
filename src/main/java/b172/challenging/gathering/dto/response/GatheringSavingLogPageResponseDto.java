package b172.challenging.gathering.dto.response;

import java.util.List;

public record GatheringSavingLogPageResponseDto(
	List<GatheringSavingLogResponseDto> gatheringSavingLogResponseDtoList,
	int pageNo,
	int pageSize,
	long totalElements,
	int totalPages,
	boolean last
) {

}
