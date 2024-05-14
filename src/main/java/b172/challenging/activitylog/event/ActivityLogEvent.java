package b172.challenging.activitylog.event;

import b172.challenging.activitylog.domain.ActivityLog;

public record ActivityLogEvent(
	ActivityLog activityLog
) {
}
