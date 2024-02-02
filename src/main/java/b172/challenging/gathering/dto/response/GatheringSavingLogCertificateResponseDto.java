package b172.challenging.gathering.dto;

import lombok.Builder;

@Builder
public record GatheringSavingLogCertificateResponseDto (
        Long amount,
        String imgUrl
){ }