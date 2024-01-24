package b172.challenging.member.dto;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import lombok.Getter;

@Getter
public class MemberDto {
    private Long id;
    private String nickname;
    public MemberDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
