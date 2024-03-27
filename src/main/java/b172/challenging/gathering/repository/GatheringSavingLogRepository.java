package b172.challenging.gathering.repository;

import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringSavingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GatheringSavingLogRepository extends JpaRepository<GatheringSavingLog, Long> {
    List<GatheringSavingLog> findAllByGatheringMember_Gathering(Gathering gathering);

    Page<GatheringSavingLog> findByGatheringMember(GatheringMember gatheringMember, Pageable pageable);
}
