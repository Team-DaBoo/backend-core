package b172.challenging.protip.service;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.common.domain.UseYn;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;
import b172.challenging.protip.dto.ProTipEditResponseDto;
import b172.challenging.protip.dto.ProTipMakeResponseDto;
import b172.challenging.protip.dto.ProTipRequestDto;
import b172.challenging.protip.dto.ProTipResponseDto;
import b172.challenging.protip.repository.ProTipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProTipService {

    private final ProTipRepository proTipRepository;
    private final MemberRepository memberRepository;

    public ProTipResponseDto findAllProTip(Role role, Pageable page) {
        Page<ProTip> proTipPage =
                role == Role.ADMIN
                        ? proTipRepository.findAll(page)
                        : proTipRepository.findByUseYnIs(UseYn.Y, page);

        List<ProTip> proTipList = new ArrayList<>(proTipPage.getContent());

        return ProTipResponseDto.builder()
                .proTips(proTipList)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(proTipPage.getTotalElements())
                .totalPages(proTipPage.getTotalPages())
                .last(proTipPage.isLast())
                .role(role)
                .build();
    }

    public ProTipResponseDto findProTipByType(Role role, ProTipType type, Pageable page) {
        Page<ProTip> proTipPage =
                role.equals(Role.ADMIN)
                    ? proTipRepository.findByType(type, page)
                    : proTipRepository.findByTypeAndUseYnIs(type, UseYn.Y, page);

        List<ProTip> proTipList = new ArrayList<>(proTipPage.getContent());

        return ProTipResponseDto.builder()
                .proTips(proTipList)
                .pageNo(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalElements(proTipPage.getTotalElements())
                .totalPages(proTipPage.getTotalPages())
                .last(proTipPage.isLast())
                .role(role)
                .build();
    }

    public ProTipMakeResponseDto putProTip(ProTipRequestDto reqeustDto) {
        return ProTipMakeResponseDto.builder()
                .proTip(proTipRepository.save(reqeustDto.toEntity()))
                .build();

    }

    @Transactional
    public ProTipEditResponseDto postProTip(Long proTipId, Long memberId, ProTipRequestDto requestDto) {
        Member member = memberRepository.getOrThrow(memberId);

        ProTip proTip = proTipRepository.findById(proTipId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_PROTIP));

        proTip.setContent(member, requestDto);

        return ProTipEditResponseDto.builder()
                .proTip(proTip)
                .build();
    }
}
