package b172.challenging.gathering.repository;

import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringSavingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GatheringSavingLogRepository extends JpaRepository<GatheringSavingLog, Long> {
    List<GatheringSavingLog> findAllByGatheringMember_Gathering(Gathering gathering);
}
