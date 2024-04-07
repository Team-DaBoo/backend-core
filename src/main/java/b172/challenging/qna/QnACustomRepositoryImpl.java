package b172.challenging.qna;

import b172.challenging.common.domain.UseYn;
import b172.challenging.common.dto.SearchRequestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static b172.challenging.member.domain.QMember.member;
import static b172.challenging.qna.QQnA.qnA;
import static org.springframework.util.StringUtils.hasText;

public class QnACustomRepositoryImpl implements QnACustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public QnACustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<QnA> searchByCriteria(SearchRequestDto searchDto, Pageable pageable) {
        List<QnA> qnAList = jpaQueryFactory
                .selectFrom(qnA)
                .where(
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
                )
                .join(member).on(qnA.registerId.eq(member))
                .offset(pageable.getOffset()) // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .orderBy(qnA.id.desc())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qnA)
                .where(
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchCriteria())
                )
                .join(member).on(qnA.registerId.eq(member))
                .fetchCount();

        return new PageImpl<>(qnAList, pageable, total);
    }

    private BooleanExpression useYnEq(UseYn useYn) {
        return useYn == null ? null : qnA.useYn.eq(useYn);
    }

    private BooleanExpression searchCriteriaEq(String searchCriteria, String searchQuery) {
        if ("nickname".equals(searchCriteria) && hasText(searchQuery)) {
            return member.nickname.contains(searchQuery);
        } else if ("title".equals(searchCriteria) && hasText(searchQuery)){
            return qnA.title.contains(searchQuery);
        }
        // 기타 searchCriteria 조건 추가
        return null;
    }
}
