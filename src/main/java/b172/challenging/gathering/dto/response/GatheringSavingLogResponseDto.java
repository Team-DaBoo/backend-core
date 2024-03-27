package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.GatheringSavingLog;

import java.time.LocalDateTime;
import java.util.List;

public record GatheringSavingLogResponseDto(
        Long id,
        Long amount,
        LocalDateTime certificatedAt,
        List<GatheringSavingCertificationResponseDto> gatheringSavingCertificationResponseDtoList
) {
    public static GatheringSavingLogResponseDto from(GatheringSavingLog gatheringSavingLog){
        return new GatheringSavingLogResponseDto(
                gatheringSavingLog.getId(),
                gatheringSavingLog.getAmount(),
                gatheringSavingLog.getCertificatedAt(),
                gatheringSavingLog.getGatheringSavingCertifications().stream().map(GatheringSavingCertificationResponseDto::from).toList()
        );
    }
}
