package b172.challenging.ranking.repository;

import b172.challenging.gathering.domain.QGatheringMember;
import b172.challenging.gathering.domain.QGatheringSavingLog;
import b172.challenging.member.domain.QMember;
import b172.challenging.myhome.domain.QMyHome;
import b172.challenging.ranking.dto.response.RankingResponseDto;
import b172.challenging.wallet.domain.QWallet;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class RankingCustomRepositoryImpl implements RankingCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public RankingCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    QGatheringSavingLog gatheringSavingLog = QGatheringSavingLog.gatheringSavingLog;
    QGatheringMember gatheringMember = QGatheringMember.gatheringMember;
    QMember member = QMember.member;
    QWallet wallet = QWallet.wallet;
    QMyHome myHome = QMyHome.myHome;

    @Override
    public Page<RankingResponseDto> findTotalRanking(String sort, Pageable page) {
        LocalDateTime startDateTime;

        if ("weekly".equals(sort)) {
            startDateTime = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.MIDNIGHT);
        } else {
            startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        }

        QueryResults<Tuple> results = jpaQueryFactory
                .select(
                        member.id,
                        member.nickname,
                        myHome.level.max(),
                        gatheringSavingLog.amount.sum())
                .from(gatheringSavingLog)
                .join(gatheringSavingLog.gatheringMember, gatheringMember)
                .join(gatheringMember.member, member)
                .join(wallet).on(member.id.eq(wallet.member.id))
                .join(wallet.myHome, myHome)
                .where(gatheringSavingLog.certificatedAt.loe(startDateTime))
                .groupBy(member.id, myHome.level)
                .orderBy(gatheringSavingLog.amount.sum().desc())
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetchResults();

        List<RankingResponseDto> rankingResponseDtos = IntStream.range(0, results.getResults().size())
                .mapToObj(i -> {
                    Tuple tuple = results.getResults().get(i);
                    long rowNum = results.getOffset() + i + 1; // 행 번호 계산
                    return RankingResponseDto.builder()
                            .rank(rowNum)
                            .memberId(tuple.get(0, Long.class))
                            .nickname(tuple.get(1, String.class))
                            .homeLevel(tuple.get(2, Long.class))
                            .totalAmount(tuple.get(3, Long.class))
                            .build();
                }).toList();
        return new PageImpl<>(rankingResponseDtos, page, results.getTotal());
    }
}
