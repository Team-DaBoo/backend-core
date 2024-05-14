package b172.challenging.gathering.dto.response;

import java.util.List;

import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.member.dto.MemberResponseDto;

public record GatheringOtherMemberResponseDto(
	List<OtherMemberResponseDto> otherMemberList
) {
	public record OtherMemberResponseDto(
		MemberResponseDto member,
		int count
	) {
		public static OtherMemberResponseDto from(GatheringMember gatheringMember) {
			return new OtherMemberResponseDto(
				MemberResponseDto.from(gatheringMember.getMember()),
				gatheringMember.getCount()
			);
		}
	}
}
