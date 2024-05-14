package b172.challenging.gathering.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import b172.challenging.gathering.domain.GatheringSavingLog;

public record GatheringSavingLogResponseDto(
	Long id,
	Long amount,
	LocalDateTime certificatedAt,
	List<GatheringSavingCertificationResponseDto> gatheringSavingCertificationResponseDtoList
) {
	public static GatheringSavingLogResponseDto from(GatheringSavingLog gatheringSavingLog) {
		return new GatheringSavingLogResponseDto(
			gatheringSavingLog.getId(),
			gatheringSavingLog.getAmount(),
			gatheringSavingLog.getCertificatedAt(),
			gatheringSavingLog.getGatheringSavingCertifications()
				.stream()
				.map(GatheringSavingCertificationResponseDto::from)
				.toList()
		);
	}
}
