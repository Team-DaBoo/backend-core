package b172.challenging.gathering.dto.response;

public record GatheringSavingLogCertificateResponseDto (
        Long amount,
        String imgUrl
){
    public static GatheringSavingLogCertificateResponseDto from(Long amount, String imgUrl) {
        return new GatheringSavingLogCertificateResponseDto(amount, imgUrl);
    }
}
