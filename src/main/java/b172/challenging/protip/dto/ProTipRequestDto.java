package b172.challenging.protip.dto;

import b172.challenging.common.domain.UseYn;
import b172.challenging.protip.domain.ProTipType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProTipRequestDto(

        @NotNull
        @Schema(description = "꿀팁 타입")
        ProTipType proTipType,

        @NotNull
        @Size(max = 30)
        @Schema(description = "꿀팁 제목")
        String title,

        @Schema(description = "꿀팁 컨텐츠")
        String content,

        @Schema(description = "이미지 url")
        String imgUrl,

        @Schema(description = "앱 링크")
        String appLinkUrl,

        @NotNull
        @Schema(description = "사용 여부")
        UseYn useYn
) { }
