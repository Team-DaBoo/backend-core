package b172.challenging.protip.repository;

import b172.challenging.admin.dto.ProTipSearchRequestDto;
import b172.challenging.common.domain.UseYn;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static b172.challenging.member.domain.QMember.member;
import static b172.challenging.protip.domain.QProTip.proTip;
import static org.springframework.util.StringUtils.hasText;

public class ProTipCustomRepositoryImpl implements ProTipCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProTipCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<ProTip> searchByCriteria(ProTipSearchRequestDto searchDto, Pageable pageable) {
        List<ProTip> proTipList = jpaQueryFactory
                .selectFrom(proTip)
                .where(
                        proTipTypeEq(searchDto.proTipType()),
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
                )
                .join(member).on(proTip.registerId.eq(member))
                .offset(pageable.getOffset()) // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .orderBy(proTip.id.desc())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(proTip)
                .where(
                        proTipTypeEq(searchDto.proTipType()),
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchCriteria())
                )
                .join(member).on(proTip.registerId.eq(member))
                .fetchCount();

        return new PageImpl<>(proTipList, pageable, total);
    }

    private BooleanExpression proTipTypeEq(ProTipType proTipType) {
        return proTipType == null ? null : proTip.proTipType.eq(proTipType);
    }
    private BooleanExpression useYnEq(UseYn useYn) {
        return useYn == null ? null : proTip.useYn.eq(useYn);
    }

    private BooleanExpression searchCriteriaEq(String searchCriteria, String searchQuery) {
        if ("nickname".equals(searchCriteria) && hasText(searchQuery)) {
            return member.nickname.contains(searchQuery);
        } else if ("title".equals(searchCriteria) && hasText(searchQuery)){
            return proTip.title.contains(searchQuery);
        }
        // 기타 searchCriteria 조건 추가
        return null;
    }
}
