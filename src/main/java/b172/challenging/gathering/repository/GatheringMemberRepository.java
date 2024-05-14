package b172.challenging.gathering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringStatus;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {

	Optional<GatheringMember> findByIdAndMemberId(Long gatheringMemberId, Long userId);

	Long countByMemberIdAndGathering_Status(Long memberId, GatheringStatus gatheringStatus);

	Long countByMemberIdAndGathering_StatusNot(Long memberId, GatheringStatus gatheringStatus);

	List<GatheringMember> findByGathering(Gathering gathering);

	Optional<GatheringMember> findByGatheringIdAndMemberId(Long gatheringId, Long memberId);

	List<GatheringMember> findByGatheringIdAndMemberIdNot(Long gatheringId, Long memberId);
}
