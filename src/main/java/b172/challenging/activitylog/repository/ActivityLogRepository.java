package b172.challenging.activitylog.repository;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    Page<ActivityLog> findByMemberId(Long memberId, Pageable page);
    Page<ActivityLog> findByMemberIdAndActivityCategory(Long memberId, ActivityCategory activityCategory, Pageable page);

}
