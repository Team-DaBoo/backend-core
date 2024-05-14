package b172.challenging.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCheckNicknameRequestDto(
	@NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
	@Size(max = 15, message = "닉네임은 15자를 초과할 수 없습니다.")
	String nickname
) {
}
