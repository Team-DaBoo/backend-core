package b172.challenging.badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.badge.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
