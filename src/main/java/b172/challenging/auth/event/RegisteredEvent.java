package b172.challenging.auth.event;

import b172.challenging.member.domain.Member;

public class RegisteredEvent {

    private final Member member;

    public RegisteredEvent(Member member) {
        this.member = member;
    }
    public Member getMember() {
        return member;
    }
}
