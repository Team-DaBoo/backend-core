package b172.challenging.member.service;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.activitylog.event.ActivityLogEvent;
import b172.challenging.auth.event.RegisteredEvent;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.dto.response.MemberCheckNicknameResponseDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberNicknameService memberNicknameService;
    private final ApplicationEventPublisher publisher;

    public Member updateMemberProfile(Long memberId, MemberProfileUpdateRequestDto memberProfileUpdateRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        String beforeNickname = member.getNickname();
        String afterNickName = memberProfileUpdateRequestDto.getNickname();
        if (member.isNicknameChanged(afterNickName)) {
            if (memberNicknameService.isNicknameExistsExcludeMe(member, afterNickName)) {
                throw new CustomRuntimeException(Exceptions.DUPLICATE_NICKNAME);
            }
            member.setNickname(afterNickName);

            publisher.publishEvent(new ActivityLogEvent(ActivityLog.createActivityLog(member, member, ActivityType.CHANGE_NICKNAME, "닉네임 변경: " + beforeNickname + " > " + afterNickName)));

        }
        member.setBirthYear(memberProfileUpdateRequestDto.getBirthYear());
        member.setSex(memberProfileUpdateRequestDto.getSex());
        if (member.getRole() == Role.GUEST) {
            member.setRole(Role.MEMBER);
            publisher.publishEvent(new RegisteredEvent(member));
        }
        memberRepository.save(member);

        return member;
    }

    public MemberCheckNicknameResponseDto checkNickname(Long memberId, String nickName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        boolean duplicate = memberNicknameService.isNicknameExistsExcludeMe(member, nickName);
        return MemberCheckNicknameResponseDto.builder()
                .duplicate(duplicate)
                .build();
    }
}
