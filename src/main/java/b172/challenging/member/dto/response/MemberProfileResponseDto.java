package b172.challenging.member.dto.response;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.domain.Role;
import b172.challenging.member.domain.Sex;

import java.time.LocalDateTime;

public record MemberProfileResponseDto (
        Long id,
        OauthProvider oauthProvider,
        String nickName,
        Role role,
        Long birthYear,
        Sex sex,
        String isLeaved,
        LocalDateTime createdAt

){
    public static MemberProfileResponseDto from(Member member) {
        return new MemberProfileResponseDto(
                member.getId(),
                member.getOauthProvider(),
                member.getNickname(),
                member.getRole(),
                member.getBirthYear(),
                member.getSex(),
                member.isLeaved() ? "탈퇴" : "정상",
                member.getCreatedAt()
        );
    }
}
