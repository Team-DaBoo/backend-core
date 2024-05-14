package b172.challenging.gathering.repository;

import static b172.challenging.gathering.domain.QGathering.*;
import static b172.challenging.gathering.domain.QGatheringMember.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import b172.challenging.member.domain.Member;

@Repository
public class GatheringMemberCustomRepositoryImpl implements GatheringMemberCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public GatheringMemberCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Integer gatheringMemberCountSum(Member member) {
		return jpaQueryFactory.select(gatheringMember.count.sum())
			.from(gatheringMember)
			.where(gatheringMember.member.eq(member))
			.fetchOne();
	}

	@Override
	public Integer gatheringMemberWorkingDaysSum(Member member) {
		return jpaQueryFactory.select(gathering.workingDays.sum())
			.from(gatheringMember)
			.leftJoin(gatheringMember.gathering, gathering)
			.where(gatheringMember.member.eq(member))
			.fetchOne();
	}
}
