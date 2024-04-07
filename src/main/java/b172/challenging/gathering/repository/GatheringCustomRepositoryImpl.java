package b172.challenging.gathering.repository;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static b172.challenging.common.util.ChallengingUtils.parseEndStringToLocalDateTime;
import static b172.challenging.common.util.ChallengingUtils.parseStartStringToLocalDateTime;
import static b172.challenging.gathering.domain.QGathering.gathering;
import static b172.challenging.member.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class GatheringCustomRepositoryImpl implements GatheringCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public GatheringCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Page<Gathering> searchByCriteria(GatheringSearchRequestDto searchDto, Pageable pageable) {
        List<Gathering> gatheringList = jpaQueryFactory
                .selectFrom(gathering)
                .where(
                        startDateAfter(searchDto.startDate()),
                        endDateBefore(searchDto.endDate()),
                        appTechPlatformEq(searchDto.appTechPlatform()),
                        statusEq(searchDto.gatheringStatus()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
                )
                .join(member).on(gathering.ownerMember.eq(member))
                .offset(pageable.getOffset()) // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .orderBy(gathering.id.desc())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(gathering)
                .where(
                        startDateAfter(searchDto.startDate()),
                        endDateBefore(searchDto.endDate()),
                        appTechPlatformEq(searchDto.appTechPlatform()),
                        statusEq(searchDto.gatheringStatus()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
                )
                .join(member).on(gathering.ownerMember.eq(member))
                .fetchCount();

        return new PageImpl<>(gatheringList, pageable, total);
    }

    private BooleanExpression startDateAfter(String startDate) {
        return hasText(startDate) ? gathering.createdAt.after(parseStartStringToLocalDateTime(startDate)) : null;
    }

    private BooleanExpression endDateBefore(String endDate) {
        return hasText(endDate) ? gathering.createdAt.before(parseEndStringToLocalDateTime(endDate)) : null;
    }

    private BooleanExpression appTechPlatformEq(AppTechPlatform appTechPlatform) {
        return appTechPlatform == null ? null : gathering.platform.eq(appTechPlatform);
    }
    private BooleanExpression statusEq(GatheringStatus gatheringStatus) {
        return gatheringStatus == null ? null : gathering.status.eq(gatheringStatus);
    }

    private BooleanExpression searchCriteriaEq(String searchCriteria, String searchQuery) {
        if ("nickname".equals(searchCriteria) && hasText(searchQuery)) {
            return member.nickname.contains(searchQuery);
        } else if ("title".equals(searchCriteria) && hasText(searchQuery)){
            return gathering.title.contains(searchQuery);
        }
        // 기타 searchCriteria 조건 추가
        return null;
    }
}
