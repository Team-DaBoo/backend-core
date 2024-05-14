package b172.challenging.gathering.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.activitylog.domain.ActivityLog;
import b172.challenging.activitylog.domain.ActivityType;
import b172.challenging.activitylog.event.ActivityLogEvent;
import b172.challenging.common.component.S3FileUploadUtil;
import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringSavingCertification;
import b172.challenging.gathering.domain.GatheringSavingLog;
import b172.challenging.gathering.dto.request.GatheringSavingLogRequestDto;
import b172.challenging.gathering.dto.response.GatheringMemberResponseDto;
import b172.challenging.gathering.dto.response.GatheringOtherMemberResponseDto;
import b172.challenging.gathering.dto.response.GatheringSavingLogCertificateResponseDto;
import b172.challenging.gathering.repository.GatheringMemberRepository;
import b172.challenging.gathering.repository.GatheringSavingCertificationRepository;
import b172.challenging.gathering.repository.GatheringSavingLogRepository;
import b172.challenging.member.domain.Member;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.repository.WalletRepository;

@Service
@RequiredArgsConstructor
public class GatheringSavingLogService {
	private final GatheringMemberRepository gatheringMemberRepository;
	private final GatheringSavingLogRepository gatheringSavingLogRepository;
	private final GatheringSavingCertificationRepository gatheringSavingCertificationRepository;
	private final WalletRepository walletRepository;

	private final S3FileUploadUtil s3FileUploadUtil;
	private final ApplicationEventPublisher publisher;

	public GatheringMemberResponseDto findMyGatheringSavingLog(Long gatheringId, Long memberId) {

		GatheringMember gatheringMember = gatheringMemberRepository.findByGatheringIdAndMemberId(gatheringId, memberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER));

		return GatheringMemberResponseDto.from(gatheringMember);
	}

	public GatheringSavingLogCertificateResponseDto saveGatheringSavingLog(Long memberId, Long gatheringMemberId,
		GatheringSavingLogRequestDto gatheringSavingLogRequestDto) {

		String imgUrl = s3FileUploadUtil.fileUpload(gatheringSavingLogRequestDto.file(),
			gatheringSavingLogRequestDto.imgUrl());

		GatheringMember gm = gatheringMemberRepository.findById(gatheringMemberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER));

		GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository.save(
			GatheringSavingLog.builder()
				.gatheringMember(gm)
				.amount(gatheringSavingLogRequestDto.amount())
				.build()
		);

		gatheringSavingCertificationRepository.save(
			GatheringSavingCertification.builder()
				.gatheringSavingLog(gatheringSavingLog)
				.imageUrl(imgUrl)
				.build()
		);

		Wallet wallet = walletRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));
		wallet.savePoint(gatheringSavingLogRequestDto.amount());
		walletRepository.save(wallet);

		ActivityLog activityLog = ActivityLog.createActivityLog(
			new Member(memberId),
			new Member(memberId),
			ActivityType.CERTIFICATE_MONEY,
			gatheringSavingLogRequestDto.amount() + " 원 인증"
		);
		publisher.publishEvent(new ActivityLogEvent(activityLog));

		return GatheringSavingLogCertificateResponseDto.from(gatheringSavingLogRequestDto.amount(), imgUrl);
	}

	@Transactional
	public GatheringSavingLogCertificateResponseDto updateGatheringSavingLog(Long memberId, Long savingLogId,
		GatheringSavingLogRequestDto gatheringSavingLogRequestDto) {

		String imgUrl = s3FileUploadUtil.fileUpload(gatheringSavingLogRequestDto.file(),
			gatheringSavingLogRequestDto.imgUrl());

		GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository.findById(savingLogId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
		Wallet wallet = walletRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));

		wallet.savePoint(gatheringSavingLogRequestDto.amount() - gatheringSavingLog.getAmount());

		ActivityLog activityLog = ActivityLog.createActivityLog(
			new Member(memberId),
			new Member(memberId),
			ActivityType.CERTIFICATE_MONEY_UPDATE,
			gatheringSavingLogRequestDto.amount() + " 원 > " + gatheringSavingLog.getAmount() + " 원 변경"
		);
		publisher.publishEvent(new ActivityLogEvent(activityLog));

		gatheringSavingLog.setAmount(gatheringSavingLogRequestDto.amount());

		GatheringSavingCertification gatheringSavingCertification =
			gatheringSavingCertificationRepository
				.findByGatheringSavingLog(gatheringSavingLog)
				.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
		gatheringSavingCertification.setImageUrl(imgUrl);

		return GatheringSavingLogCertificateResponseDto.from(gatheringSavingLogRequestDto.amount(), imgUrl);
	}

	public GatheringOtherMemberResponseDto findOtherMemberGatheringSavingLog(Long gatheringId, Long memberId) {
		List<GatheringMember> gatheringMemberList = gatheringMemberRepository.findByGatheringIdAndMemberIdNot(
			gatheringId, memberId);

		if (gatheringMemberList.isEmpty()) {
			throw new CustomRuntimeException(Exceptions.NOT_FOUND_GATHERING_MEMBER);
		}

		return gatheringMemberList.stream().map(GatheringOtherMemberResponseDto.OtherMemberResponseDto::from).collect(
			collectingAndThen(
				toList(),
				GatheringOtherMemberResponseDto::new
			)
		);
	}

	public void deleteGatheringSavingLog(Long memberId, Long gatheringSavingLogId) {
		GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository.findById(gatheringSavingLogId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

		Wallet wallet = walletRepository.findByMemberId(memberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));

		wallet.savePoint(-gatheringSavingLog.getAmount());
		walletRepository.save(wallet);

		gatheringSavingLogRepository.delete(gatheringSavingLog);
	}
}
