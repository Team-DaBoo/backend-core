package b172.challenging.gathering.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface GatheringRepository extends JpaRepository<Gathering,Long> {
    Page<Gathering> findByGatheringMembersMemberAndStatus(Member member,GatheringStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMemberAndStatusNot(Member member,GatheringStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMemberNotAndStatus(Member member,GatheringStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMemberNotAndStatusNot(Member member,GatheringStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMemberNotAndPlatformAndStatus(Member member, AppTechPlatform platform, GatheringStatus gatheringStatus, Pageable page);
    Page<Gathering> findByGatheringMembersMemberNotAndPlatformAndStatusNot(Member member, AppTechPlatform platform, GatheringStatus gatheringStatus, Pageable page);


    Page<Gathering> findByPlatformAndStatus(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByPlatformAndStatusNot(AppTechPlatform platform, GatheringStatus status , Pageable page);

    Page<Gathering> findByStatus(GatheringStatus status, Pageable page);

    Page<Gathering> findByStatusNot(GatheringStatus status, Pageable page);


    Long countByOwnerMember(Member member);
}
