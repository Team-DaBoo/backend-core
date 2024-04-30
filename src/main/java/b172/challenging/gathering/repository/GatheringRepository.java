package b172.challenging.gathering.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GatheringRepository extends JpaRepository<Gathering,Long>,GatheringCustomRepository {
    Page<Gathering> findByGatheringMembersMember_IdAndGatheringMembers_IsActive(Long memberId, Boolean isActive, Pageable page);
    Page<Gathering> findByOwnerMember_IdAndGatheringMembers_IsActive(Long memberId,Boolean isActive, Pageable page);


    Page<Gathering> findByPlatform(AppTechPlatform platform, Pageable page);
    Page<Gathering> findByPlatformAndStatus(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByPlatformAndStatusNot(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByStatus(GatheringStatus status, Pageable page);

    Page<Gathering> findByStatusNot(GatheringStatus status, Pageable page);

    Optional<Gathering> findByIdAndStatus(Long gatheringId , GatheringStatus gatheringStatus);

    Long countByOwnerMember(Member member);
}
