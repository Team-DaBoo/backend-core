package b172.challenging.badge.dto.response;

import b172.challenging.badge.domain.BadgeMember;

public record BadgeMemberResponseDto(

        Long memberId,


        BadgeResponseDto badge

) {
    public static BadgeMemberResponseDto from(BadgeMember badgeMember){
        return new BadgeMemberResponseDto(
                badgeMember.getId(),
                BadgeResponseDto.from(badgeMember.getBadge())
        );
    }
}
