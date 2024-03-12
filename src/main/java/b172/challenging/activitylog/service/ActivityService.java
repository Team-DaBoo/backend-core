package b172.challenging.activitylog.service;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.activitylog.dto.ActivityLogDto;
import b172.challenging.activitylog.dto.ActivityLogPageResponseDto;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityLogRepository activityLogRepository;


    public ActivityLogPageResponseDto findActivityLog(Long memberId, ActivityCategory activityCategory, Pageable page) {

        Page<ActivityLog> activityLogPage;

        activityLogPage = (activityCategory == null)
                ? activityLogRepository.findByMemberId(memberId, page)
                : activityLogRepository.findByMemberIdAndActivityCategory(memberId,activityCategory, page);



        if(activityLogPage.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_ACTIVITY_LOG);
        }

        List<ActivityLog> activityLogList = new ArrayList<>(activityLogPage.getContent());
        List<ActivityLogDto> activityLogResponseDto = activityLogList.stream()
                .map(ActivityLogDto::new)
                .toList();

        return ActivityLogPageResponseDto.builder()
                .activityLogResponseDto(activityLogResponseDto)
                .pageNo(activityLogPage.getNumber())
                .pageSize(activityLogPage.getSize())
                .totalElements(activityLogPage.getTotalElements())
                .totalPages(activityLogPage.getTotalPages())
                .lastPage(activityLogPage.isLast())
                .build();

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
