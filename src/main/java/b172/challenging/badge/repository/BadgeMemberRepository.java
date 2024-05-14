package b172.challenging.badge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.badge.domain.BadgeMember;

public interface BadgeMemberRepository extends JpaRepository<BadgeMember, Long> {
	List<BadgeMember> findByMemberId(Long memberId);

	boolean existsByBadgeIdAndMemberId(Long badgeId, Long memberId);

	BadgeMember findByBadgeIdAndMemberId(Long badgeId, Long memberId);

	int countByMemberId(Long memberId);
}
