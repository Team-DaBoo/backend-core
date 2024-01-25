package b172.challenging.member.dto;

import b172.challenging.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    private final Long id;
    private final String nickname;
    public MemberDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
