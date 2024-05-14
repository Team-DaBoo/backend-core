package b172.challenging.gathering.dto.request;

import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public record GatheringSavingLogRequestDto(
	@NotBlank(message = "인증한 금액을 입력 해야 합니다.")
	Long amount,

	String imgUrl,

	MultipartFile file
) {
}
