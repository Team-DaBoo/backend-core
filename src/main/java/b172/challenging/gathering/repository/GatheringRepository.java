package b172.challenging.gathering.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.gathering.domain.AppTechPlatform;
import b172.challenging.gathering.domain.Gathering;
import b172.challenging.gathering.domain.GatheringStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface GatheringRepository extends JpaRepository<Gathering,Long> {
    @EntityGraph(attributePaths = "ownerMember")
    Page<Gathering> findByPlatformAndStatus(AppTechPlatform platform, GatheringStatus status , Pageable page);
    @EntityGraph(attributePaths = "ownerMember")
    Page<Gathering> findByPlatformAndStatusNot(AppTechPlatform platform, GatheringStatus status , Pageable page);
    @EntityGraph(attributePaths = {"ownerMember"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<Gathering> findByStatus(GatheringStatus status, Pageable page);
    @EntityGraph(attributePaths = "ownerMember")
    Page<Gathering> findByStatusNot(GatheringStatus status, Pageable page);

    Long countByOwnerMember(Member member);
}
