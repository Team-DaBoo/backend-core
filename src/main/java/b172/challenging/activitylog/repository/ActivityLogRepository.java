package b172.challenging.activitylog.repository;

import b172.challenging.activitylog.domain.ActivityCategory;
import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    Page<ActivityLog> findAll(Pageable page);
    Page<ActivityLog> findByMember(Member member, Pageable page);

    Page<ActivityLog> findByMemberAndActivityCategory(Member member, ActivityCategory activityCategory, Pageable page);
}
