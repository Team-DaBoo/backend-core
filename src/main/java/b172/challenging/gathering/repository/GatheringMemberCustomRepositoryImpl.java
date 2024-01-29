package b172.challenging.gathering.repository;

import b172.challenging.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static b172.challenging.gathering.domain.QGatheringMember.gatheringMember;
import static b172.challenging.gathering.domain.QGathering.gathering;


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
