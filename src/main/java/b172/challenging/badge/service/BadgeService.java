package b172.challenging.badge.service;

import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.badge.domain.BadgeMember;
import b172.challenging.badge.repository.BadgeMemberRepository;
import b172.challenging.badge.dto.response.BadgeMemberResponseDto;
import b172.challenging.badge.dto.response.BadgeResponseDto;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeMemberRepository badgeMemberRepository;

    public BadgeMemberResponseDto findMemberBadgeList(Long memberId) {

        List<BadgeMember> badgeMemberList = badgeMemberRepository.findByMemberId(memberId);

        if(badgeMemberList.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_BADGE);
        }

        List<BadgeResponseDto> badgeResponseDtos = badgeMemberList.stream()
                .map(badgeMember -> {
                    return BadgeResponseDto.builder()
                            .id(badgeMember.getBadge().getId())
                            .name(badgeMember.getBadge().getName())
                            .description(badgeMember.getBadge().getDescription())
                            .imageUrl(badgeMember.getBadge().getImageUrl())
                            .createdAt(badgeMember.getCreatedAt())
                            .build();
                })
                .toList();

        return BadgeMemberResponseDto.builder()
                .memberId(memberId)
                .badges(badgeResponseDtos)
                .build();
    }
}
