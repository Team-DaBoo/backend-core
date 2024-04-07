package b172.challenging.announcements;

import b172.challenging.common.domain.UseYn;
import b172.challenging.common.dto.SearchRequestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static b172.challenging.announcements.QAnnouncements.announcements;
import static b172.challenging.member.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class AnnouncementsCustomRepositoryImpl implements AnnouncementsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AnnouncementsCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Announcements> searchByCriteria(SearchRequestDto searchDto, Pageable pageable) {
        List<Announcements> announcementsList = jpaQueryFactory
                .selectFrom(announcements)
                .where(
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchQuery())
                )
                .join(member).on(announcements.registerId.eq(member))
                .offset(pageable.getOffset()) // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .orderBy(announcements.id.desc())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(announcements)
                .where(
                        useYnEq(searchDto.useYn()),
                        searchCriteriaEq(searchDto.searchCriteria(), searchDto.searchCriteria())
                )
                .join(member).on(announcements.registerId.eq(member))
                .fetchCount();

        return new PageImpl<>(announcementsList, pageable, total);
    }

    private BooleanExpression useYnEq(UseYn useYn) {
        return useYn == null ? null : announcements.useYn.eq(useYn);
    }

    private BooleanExpression searchCriteriaEq(String searchCriteria, String searchQuery) {
        if ("nickname".equals(searchCriteria) && hasText(searchQuery)) {
            return member.nickname.contains(searchQuery);
        } else if ("title".equals(searchCriteria) && hasText(searchQuery)){
            return announcements.title.contains(searchQuery);
        }
        // 기타 searchCriteria 조건 추가
        return null;
    }
}
