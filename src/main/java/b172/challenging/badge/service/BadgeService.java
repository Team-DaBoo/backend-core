package b172.challenging.badge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import b172.challenging.badge.domain.Badge;
import b172.challenging.badge.domain.BadgeMember;
import b172.challenging.badge.dto.response.BadgeDto;
import b172.challenging.badge.repository.BadgeMemberRepository;
import b172.challenging.badge.repository.BadgeRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {

	private final BadgeMemberRepository badgeMemberRepository;
	private final BadgeRepository badgeRepository;
	private final MemberRepository memberRepository;

	public List<BadgeDto> findMemberBadgeList(Long memberId) {
		List<Badge> badgeList = badgeRepository.findAll();

		return badgeList.stream().map(
			badge -> new BadgeDto(
				badge.getId(),
				badge.getName(),
				badge.getDescription(),
				badgeMemberRepository.existsByBadgeIdAndMemberId(badge.getId(), memberId)
			)
		).toList();
	}

	public int countBadgeByMemberId(Long memberId) {
		return badgeMemberRepository.countByMemberId(memberId);
	}

	public void deleteMemberBadge(Long memberId, Long badgeId) {
		badgeMemberRepository.delete(
			badgeMemberRepository.findByBadgeIdAndMemberId(badgeId, memberId)
		);
	}

	public void insertMemberBadge(Long memberId, Long badgeId) {
		Member member = memberRepository.getOrThrow(memberId);
		Badge badge = badgeRepository.findById(badgeId).orElseThrow();
		BadgeMember badgeMember = BadgeMember.builder().badge(badge).member(member).build();
		badgeMemberRepository.save(badgeMember);
	}
}
