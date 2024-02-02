package b172.challenging.member.service;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.dto.request.MemberProfileUpdateRequestDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.ErrorCode;
import b172.challenging.member.service.MemberNicknameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberNicknameService memberNicknameService;


    public Member updateMemberProfile(Long memberId, MemberProfileUpdateRequestDto memberProfileUpdateRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER));
        if (member.isNicknameChanged(memberProfileUpdateRequestDto.getNickname())) {
            memberNicknameService.isNicknameExists(memberProfileUpdateRequestDto.getNickname());
            member.setNickname(memberProfileUpdateRequestDto.getNickname());
        }
        if (member.getRole() == Role.GUEST) {
            member.setRole(Role.MEMBER);
        }
        memberRepository.save(member);
        return member;
    }
}
