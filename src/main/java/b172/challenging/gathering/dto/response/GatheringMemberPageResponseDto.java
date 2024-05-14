package b172.challenging.gathering.dto.response;

import java.util.List;

public record GatheringMemberPageResponseDto(
	List<GatheringMemberResponseDto> gatheringMemberList,
	int pageNo,
	int pageSize,
	long totalElements,
	int totalPages,
	boolean last
) {

}
