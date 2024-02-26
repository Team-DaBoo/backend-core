package b172.challenging.member.service;

import b172.challenging.auth.event.RegisteredEvent;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.dto.response.MemberCheckNicknameResponseDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.service.MemberNicknameService;
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
        if (member.isNicknameChanged(memberProfileUpdateRequestDto.getNickname())) {
            if (memberNicknameService.isNicknameExistsExcludeMe(member, memberProfileUpdateRequestDto.getNickname())) {
                throw new CustomRuntimeException(Exceptions.DUPLICATE_NICKNAME);
            }
            member.setNickname(memberProfileUpdateRequestDto.getNickname());
        }
        member.setBirthYear(memberProfileUpdateRequestDto.getBirthYear());
        member.setSex(memberProfileUpdateRequestDto.getSex());
        if (member.getRole() == Role.GUEST) {
            member.setRole(Role.MEMBER);
        }
        memberRepository.save(member);

        publisher.publishEvent(new RegisteredEvent(member));
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
