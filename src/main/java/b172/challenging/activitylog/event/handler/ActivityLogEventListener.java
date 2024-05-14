package b172.challenging.activitylog.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.event.ActivityLogEvent;
import b172.challenging.activitylog.repository.ActivityLogRepository;

@Component
@RequiredArgsConstructor
public class ActivityLogEventListener {
	private final ActivityLogRepository activityLogRepository;

	@EventListener
	@Async
	public void saveActivityLogEventHandler(ActivityLogEvent event) {
		ActivityLog activityLog = event.activityLog();
		activityLogRepository.save(activityLog);
	}

}
