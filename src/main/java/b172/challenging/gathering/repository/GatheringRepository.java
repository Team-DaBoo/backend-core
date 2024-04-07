package b172.challenging.gathering.repository;

import b172.challenging.gathering.domain.GatheringMemberStatus;
import b172.challenging.member.domain.Member;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface GatheringRepository extends JpaRepository<Gathering,Long>,GatheringCustomRepository {
    Page<Gathering> findByGatheringMembersMember_IdAndGatheringMembersStatus(Long memberId, GatheringMemberStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMember_IdAndGatheringMembersStatusNot(Long memberId,GatheringMemberStatus gatheringStatus, Pageable page);
    Page<Gathering> findByOwnerMember_IdAndGatheringMembersStatus(Long memberId,GatheringMemberStatus gatheringMemberStatus, Pageable page);
    Page<Gathering> findByOwnerMember_IdAndGatheringMembersStatusNot(Long memberId,GatheringMemberStatus gatheringMemberStatus, Pageable page);


    Page<Gathering> findByPlatform(AppTechPlatform platform, Pageable page);
    Page<Gathering> findByPlatformAndStatus(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByPlatformAndStatusNot(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByStatus(GatheringStatus status, Pageable page);

    Page<Gathering> findByStatusNot(GatheringStatus status, Pageable page);


    Long countByOwnerMember(Member member);
}
