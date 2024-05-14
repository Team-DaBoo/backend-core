package b172.challenging.activitylog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.dto.ActivityLogResponseDto;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import b172.challenging.common.dto.PageResponse;

@RequiredArgsConstructor
@Service
public class ActivityService {

	private final ActivityLogRepository activityLogRepository;

	public PageResponse<ActivityLogResponseDto> findActivityLog(Long memberId, ActivityCategory activityCategory,
		Pageable page) {

		if (memberId == null) {
			return PageResponse.from(activityLogRepository.findAll(page).map(ActivityLogResponseDto::from));
		}

		Page<ActivityLog> activityLogPage = (activityCategory == null)
			? activityLogRepository.findByMemberId(memberId, page)
			: activityLogRepository.findByMemberIdAndActivityCategory(memberId, activityCategory, page);

		return PageResponse.from(activityLogPage.map(ActivityLogResponseDto::from));

	}
}
