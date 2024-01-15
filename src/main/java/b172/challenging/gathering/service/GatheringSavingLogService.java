package b172.challenging.gathering.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.gathering.domain.GatheringMember;
import b172.challenging.gathering.domain.GatheringSavingCertification;
import b172.challenging.gathering.domain.GatheringSavingLog;
import b172.challenging.gathering.dto.GatheringSavingLogCertificateResponseDto;
import b172.challenging.gathering.dto.GatheringSavingLogResponseDto;
import b172.challenging.gathering.dto.request.GatheringSavingLogRequestDto;
import b172.challenging.gathering.repository.GatheringSavingCertificationRepository;
import b172.challenging.gathering.repository.GatheringSavingLogRepository;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GatheringSavingLogService {
    private final GatheringSavingCertificationRepository gatheringSavingCertificationRepository;
    private final GatheringSavingLogRepository gatheringSavingLogRepository;

    private final WalletRepository walletRepository;

    public GatheringSavingLogResponseDto findGatheringSavingLog(Long savingLogId){
        GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository
                        .findById(savingLogId)
                        .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        return GatheringSavingLogResponseDto.builder()
                .gatheringSavingLog(gatheringSavingLog)
                .build();
    }

    public GatheringSavingLogCertificateResponseDto saveGatheringSavingLog(Long memberId, Long gatheringMemberId, GatheringSavingLogRequestDto gatheringSavingLogRequestDto) {
        GatheringMember gm = new GatheringMember(gatheringMemberId);

        GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository.save(
                GatheringSavingLog.builder()
                        .gatheringMember(gm)
                        .amount(gatheringSavingLogRequestDto.amount())
                        .build()
        );

        gatheringSavingCertificationRepository.save(
                GatheringSavingCertification.builder()
                        .gatheringSavingLog(gatheringSavingLog)
                        .imageUrl(gatheringSavingLogRequestDto.imgUrl())
                        .build()
        );

        Wallet wallet = walletRepository.findByMemberId(memberId).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        wallet.savePoint(gatheringSavingLogRequestDto.amount());
        walletRepository.save(wallet);

        return GatheringSavingLogCertificateResponseDto.builder()
                .amount(gatheringSavingLogRequestDto.amount())
                .imgUrl(gatheringSavingLogRequestDto.imgUrl())
                .build();
    }

    @Transactional
    public GatheringSavingLogCertificateResponseDto updateGatheringSavingLog(Long memberId, Long savingLogId, GatheringSavingLogRequestDto gatheringSavingLogRequestDto) {

        GatheringSavingLog gatheringSavingLog = gatheringSavingLogRepository.findById(savingLogId).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        gatheringSavingLog.setAmount(gatheringSavingLogRequestDto.amount());

        Wallet wallet = walletRepository.findByMemberId(memberId).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        wallet.savePoint(gatheringSavingLogRequestDto.amount() - gatheringSavingLog.getAmount() + gatheringSavingLogRequestDto.amount());

        GatheringSavingCertification gatheringSavingCertification = gatheringSavingCertificationRepository.findByGatheringSavingLog(gatheringSavingLog).orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        gatheringSavingCertification.setImageUrl(gatheringSavingLogRequestDto.imgUrl());

        return GatheringSavingLogCertificateResponseDto.builder()
                .amount(gatheringSavingLogRequestDto.amount())
                .imgUrl(gatheringSavingLogRequestDto.imgUrl())
                .build();
    }
}
