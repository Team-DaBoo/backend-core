package b172.challenging.gathering.dto.response;

import b172.challenging.gathering.domain.GatheringSavingCertification;

import java.time.LocalDateTime;

public record GatheringSavingCertificationResponseDto(
        Long id,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GatheringSavingCertificationResponseDto from(GatheringSavingCertification gatheringSavingCertification){
        return new GatheringSavingCertificationResponseDto(
                gatheringSavingCertification.getId(),
                gatheringSavingCertification.getImageUrl(),
                gatheringSavingCertification.getCreatedAt(),
                gatheringSavingCertification.getUpdatedAt()
        );
    }
}
