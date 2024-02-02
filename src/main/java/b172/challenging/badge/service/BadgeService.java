package b172.challenging.badge.service;

import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.badge.domain.Badge;
import b172.challenging.badge.dto.response.BadgeMemberResponseDto;
import b172.challenging.badge.dto.response.BadgeResponseDto;
import b172.challenging.badge.repository.BadgeCustomRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {

    private final MemberRepository memberRepository;
    private final BadgeCustomRepository badgeCustomRepository;

    public BadgeMemberResponseDto findMemberBadgeList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));

        List<Tuple> badgeMemberList = badgeCustomRepository.findBadgeMemberWithBadgeByMemberId(memberId);

        List<BadgeResponseDto> badgeResponseDtos = badgeMemberList.stream()
                .map(tuple -> {
                    Badge badge = tuple.get(0, Badge.class);
                    Boolean hasBadge = tuple.get(1, Boolean.class);
                    return new BadgeResponseDto(
                            badge.getId(),
                            badge.getName(),
                            badge.getDescription(),
                            badge.getImageUrl(),
                            hasBadge
                    );
                })
                .collect(Collectors.toList());

        return BadgeMemberResponseDto.builder()
                .badges(badgeResponseDtos)
                .build();
    }
}
