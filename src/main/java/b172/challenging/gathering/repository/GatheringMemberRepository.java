package b172.challenging.gathering.repository;

import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringMemberStatus;
import b172.challenging.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {
    List<GatheringMember> findByGatheringAndStatus(Gathering gathering, GatheringMemberStatus gatheringMemberStatus);

    Optional<GatheringMember> findByIdAndMemberId(Long gatheringMemberId, Long userId);
    Optional<GatheringMember> findByMember(Member member);

    Long countByMemberIdAndStatus(Long memberId, GatheringMemberStatus gatheringMemberStatus);

    List<GatheringMember> findByGatheringId(Long gatheringId);
    List<GatheringMember> findByGathering(Gathering gathering);

    Optional<GatheringMember> findByGatheringIdAndMemberId(Long gatheringId, Long memberId);
    List<GatheringMember> findByGatheringIdAndMemberIdNot(Long gatheringId, Long memberId);
}
