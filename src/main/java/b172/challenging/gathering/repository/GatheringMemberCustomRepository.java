package b172.challenging.gathering.repository;

import b172.challenging.member.domain.Member;

public interface GatheringMemberCustomRepository {

	Integer gatheringMemberCountSum(Member member);

	Integer gatheringMemberWorkingDaysSum(Member member);
}
