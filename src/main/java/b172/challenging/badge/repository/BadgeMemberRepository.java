package b172.challenging.badge.repository;

import b172.challenging.badge.domain.BadgeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeMemberRepository extends JpaRepository<BadgeMember, Long> {
    List<BadgeMember> findByMemberId(Long memberId);
    boolean existsByBadgeIdAndMemberId(Long badgeId, Long memberId);

    BadgeMember findByBadgeIdAndMemberId(Long badgeId, Long memberId);

    int countByMemberId(Long memberId);
}
