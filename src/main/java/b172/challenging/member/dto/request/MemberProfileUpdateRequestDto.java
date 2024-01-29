package b172.challenging.member.dto.request;

import b172.challenging.member.domain.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberProfileUpdateRequestDto {

    @NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
    @Size(max = 15, message = "닉네임은 15자를 초과할 수 없습니다.")
    private String nickname;

    @Schema(description = "생년")
    private Long birthYear;

    @Schema(description = "성별")
    private Sex sex;
}