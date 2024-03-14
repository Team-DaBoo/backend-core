package b172.challenging.activitylog.service;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.activitylog.dto.ActivityLogResponseDto;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityLogRepository activityLogRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    public Page<ActivityLogResponseDto> findActivityLog(Long memberId, ActivityCategory activityCategory, Pageable page) {

        if(memberId == null) return activityLogRepository.findAll(page).map(ActivityLogResponseDto::from);

        Member member = memberRepository.getOrThrow(memberId);

        Page<ActivityLog> activityLogPage = (activityCategory == null)
                ? activityLogRepository.findByMember(member, page)
                : activityLogRepository.findByMemberAndActivityCategory(member,activityCategory, page);

        if(activityLogPage.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_ACTIVITY_LOG);
        }

        return activityLogPage.map(ActivityLogResponseDto::from);

    }



    public void saveActivityLog(Member member, ActivityCategory activityCategory, ActivityType activityType, String description) {
        ActivityLog activityLog = ActivityLog.builder()
                .member(member)
                .activityCategory(activityCategory)
                .activityType(activityType)
                .description(description)
                .build();
        activityLogRepository.save(activityLog);
    }
}
