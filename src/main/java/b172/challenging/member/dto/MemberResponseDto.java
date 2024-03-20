package b172.challenging.member.dto;

import b172.challenging.member.domain.Member;

public record MemberResponseDto (
        Long id,
        String nickname
){
    public static MemberResponseDto from(Member member){
        return new MemberResponseDto(
                member.getId(),
                member.getNickname()
        );
    }
}
