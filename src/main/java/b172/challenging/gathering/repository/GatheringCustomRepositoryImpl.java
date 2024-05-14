package b172.challenging.gathering.repository;

import static b172.challenging.common.util.ChallengingUtils.*;
import static b172.challenging.gathering.domain.QGathering.*;
import static b172.challenging.gathering.domain.QGatheringMember.*;
import static b172.challenging.member.domain.QMember.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;

public class GatheringCustomRepositoryImpl implements GatheringCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public GatheringCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Page<Gathering> searchByCriteria(GatheringSearchRequestDto searchDto, Pageable pageable) {
		JPAQuery<Gathering> query = jpaQueryFactory
			.selectFrom(gathering)
			.where(
				startDateAfter(searchDto.startDate()),
				endDateBefore(searchDto.endDate()),
				appTechPlatformEq(searchDto.appTechPlatform()),
				statusEq(searchDto.gatheringStatus()),
				searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
			);
		if ("nickname".equals(searchDto.searchCriteria())) {
			query.join(gatheringMember).on(
				gathering.eq(gatheringMember.gathering)
					.and(gatheringMember.member.nickname.eq(searchDto.searchQuery()))
			);
		} else {
			query.join(member).on(gathering.ownerMember.eq(member));
		}

		query
			.offset(pageable.getOffset()) // 페이지 시작 위치
			.limit(pageable.getPageSize()) // 페이지 크기
			.orderBy(gathering.id.desc());

		List<Gathering> gatheringList = query.fetch();

		long total = query.fetch().size();

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
		if ("owner_nickname".equals(searchCriteria) && hasText(searchQuery)) {
			return member.nickname.contains(searchQuery);
		} else if ("title".equals(searchCriteria) && hasText(searchQuery)) {
			return gathering.title.contains(searchQuery);
		}
		// 기타 searchCriteria 조건 추가
		return null;
	}
}
