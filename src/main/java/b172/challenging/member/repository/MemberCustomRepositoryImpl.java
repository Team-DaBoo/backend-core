package b172.challenging.member.repository;

import static b172.challenging.common.util.ChallengingUtils.*;
import static b172.challenging.member.domain.QMember.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import b172.challenging.admin.dto.MemberSearchRequestDto;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.domain.Sex;

@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public MemberCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Optional<String> findJwtCodeById(Long id) {
		String jwtCode = jpaQueryFactory
			.select(member.jwtCode)
			.from(member)
			.where(member.id.eq(id))
			.fetchOne();
		return Optional.ofNullable(jwtCode);
	}

	@Override
	@Transactional
	public Long updateJwtCodeById(Long memberId, String jwtCode) {
		return jpaQueryFactory
			.update(member)
			.set(member.jwtCode, jwtCode)
			.where(member.id.eq(memberId))
			.execute();
	}

	@Override
	public Page<Member> searchByCriteria(MemberSearchRequestDto searchDto, Pageable pageable) {
		List<Member> memberList = jpaQueryFactory
			.selectFrom(member)
			.where(
				startDateAfter(searchDto.startDate()),
				endDateBefore(searchDto.endDate()),
				sexEq(searchDto.sex()),
				isLeavedEq(searchDto.isLeaved()),
				oauthProviderEq(searchDto.oauthProvider()),
				searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
			)
			.offset(pageable.getOffset()) // 페이지 시작 위치
			.limit(pageable.getPageSize()) // 페이지 크기
			.orderBy(member.id.desc())
			.fetch();

		long total = jpaQueryFactory
			.selectFrom(member)
			.where(
				startDateAfter(searchDto.startDate()),
				endDateBefore(searchDto.endDate()),
				sexEq(searchDto.sex()),
				isLeavedEq(searchDto.isLeaved()),
				oauthProviderEq(searchDto.oauthProvider()),
				searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
			)
			.fetchCount();
		return new PageImpl<>(memberList, pageable, total);
	}

	private BooleanExpression startDateAfter(String startDate) {
		return hasText(startDate) ? member.createdAt.after(parseStartStringToLocalDateTime(startDate)) : null;
	}

	private BooleanExpression endDateBefore(String endDate) {
		return hasText(endDate) ? member.createdAt.before(parseEndStringToLocalDateTime(endDate)) : null;
	}

	private BooleanExpression sexEq(Sex sex) {
		return sex == null ? null : member.sex.eq(sex);
	}

	private BooleanExpression isLeavedEq(Boolean isLeaved) {
		return isLeaved == null ? null : member.isLeaved.eq(isLeaved);
	}

	private BooleanExpression oauthProviderEq(OauthProvider oauthProvider) {
		return oauthProvider == null ? null : member.oauthProvider.eq(oauthProvider);
	}

	private BooleanExpression searchCriteriaEq(String searchCriteria, String searchQuery) {
		if ("nickname".equals(searchCriteria) && hasText(searchQuery)) {
			return member.nickname.contains(searchQuery);
		}
		// 기타 searchCriteria 조건 추가
		return null;
	}
}
