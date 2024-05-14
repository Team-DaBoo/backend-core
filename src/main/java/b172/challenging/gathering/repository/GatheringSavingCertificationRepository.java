package b172.challenging.gathering.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.gathering.domain.GatheringSavingCertification;
import b172.challenging.gathering.domain.GatheringSavingLog;

public interface GatheringSavingCertificationRepository extends JpaRepository<GatheringSavingCertification, Long> {
	Optional<GatheringSavingCertification> findByGatheringSavingLog(GatheringSavingLog gatheringSavingLog);
}
