package b172.challenging.gathering.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.member.dto.MemberResponseDto;

public record GatheringMemberResponseDto(
	Long id,
	MemberResponseDto member,
	Boolean isActive,
	Long amount,
	int count,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	List<GatheringSavingLogResponseDto> gatheringSavingLogResponseDtoList,
	long gatheringSavingLogResponseDtoListSize
) {
	public static GatheringMemberResponseDto from(GatheringMember gatheringMember) {
		return new GatheringMemberResponseDto(
			gatheringMember.getId(),
			MemberResponseDto.from(gatheringMember.getMember()),
			gatheringMember.getIsActive(),
			gatheringMember.getAmount(),
			gatheringMember.getCount(),
			gatheringMember.getCreatedAt(),
			gatheringMember.getUpdatedAt(),
			gatheringMember.getGatheringSavingLogs().stream().map(GatheringSavingLogResponseDto::from).toList(),
			gatheringMember.getGatheringSavingLogs().size()
		);
	}
}
