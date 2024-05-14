package b172.challenging.protip.service;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.admin.dto.ProTipSearchRequestDto;
import b172.challenging.common.domain.UseYn;
import b172.challenging.common.dto.PageResponse;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.Role;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;
import b172.challenging.protip.dto.ProTipRequestDto;
import b172.challenging.protip.dto.ProTipResponseDto;
import b172.challenging.protip.repository.ProTipRepository;

@Service
@RequiredArgsConstructor
public class ProTipService {

	private final ProTipRepository proTipRepository;
	private final MemberRepository memberRepository;

	public PageResponse<ProTipResponseDto> findAllProTip(Role role, ProTipSearchRequestDto proTipSearchRequestDto,
		Pageable page) {
		Page<ProTip> proTipPage =
			role == Role.ADMIN
				? proTipRepository.searchByCriteria(proTipSearchRequestDto, page)
				: proTipRepository.findByUseYnIs(UseYn.Y, page);

		return PageResponse.from(proTipPage.map(ProTipResponseDto::from));
	}

	public PageResponse<ProTipResponseDto> findProTipByType(Role role, ProTipType proTipType, Pageable page) {
		Page<ProTip> proTipPage =
			role.equals(Role.ADMIN)
				? proTipRepository.findByProTipType(proTipType, page)
				: proTipRepository.findByProTipTypeAndUseYnIs(proTipType, UseYn.Y, page);

		return PageResponse.from(proTipPage.map(ProTipResponseDto::from));
	}

	public void postProTip(Long proTipId, Long memberId, ProTipRequestDto requestDto) {
		Member member = memberRepository.getOrThrow(memberId);

		ProTip proTip = proTipRepository.findById(proTipId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_PROTIP));

		proTip.setContent(member, requestDto);

		proTipRepository.save(proTip);
	}

	public void createProTip(Long memberId, ProTipRequestDto requestDto) {
		Member member = memberRepository.getOrThrow(memberId);

		ProTip proTip = new ProTip();
		proTip.setContent(member, requestDto);

		proTipRepository.save(proTip);
	}

	public ProTip findProTipById(Long id) {
		return proTipRepository.findById(id)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_PROTIP));
	}

	@Transactional
	public void deleteProTip(Long id) {
		proTipRepository.deleteById(id);
	}
}
