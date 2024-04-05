package b172.challenging.activitylog.service;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.dto.ActivityLogResponseDto;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityLogRepository activityLogRepository;

    public PageResponse<ActivityLogResponseDto> findActivityLog(Long memberId, ActivityCategory activityCategory, Pageable page) {

        if(memberId == null) return PageResponse.from(activityLogRepository.findAll(page).map(ActivityLogResponseDto::from));

        Page<ActivityLog> activityLogPage = (activityCategory == null)
                ? activityLogRepository.findByMemberId(memberId, page)
                : activityLogRepository.findByMemberIdAndActivityCategory(memberId,activityCategory, page);

        return PageResponse.from(activityLogPage.map(ActivityLogResponseDto::from));

    }
}
