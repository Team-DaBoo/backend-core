package b172.challenging.activitylog.event.handler;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.event.ActivityLogEvent;
import b172.challenging.activitylog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityLogEventListener {
    private final ActivityLogRepository activityLogRepository;

    @EventListener
    @Async
    public void saveActivityLogEventHandler(ActivityLogEvent event) {
        ActivityLog activityLog = event.activityLog();
        System.out.println(activityLog.getId());
        activityLogRepository.save(activityLog);
    }

}
