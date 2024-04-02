package b172.challenging.qna;

import b172.challenging.common.domain.UseYn;

public record QnARequestDto(
        String title,
        String content,
        UseYn useYn
) {
}
