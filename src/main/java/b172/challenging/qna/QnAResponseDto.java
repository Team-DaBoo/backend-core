package b172.challenging.qna;

import b172.challenging.common.domain.UseYn;
import b172.challenging.member.dto.MemberResponseDto;

import java.time.LocalDateTime;

public record QnAResponseDto(
        Long id,
        String title,
        String content,
        MemberResponseDto registerId,
        UseYn useYn,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public static QnAResponseDto from(QnA qnA){
        return new QnAResponseDto(
                qnA.getId(),
                qnA.getTitle(),
                qnA.getContent(),
                MemberResponseDto.from(qnA.getRegisterId()),
                qnA.getUseYn(),
                qnA.getCreatedAt(),
                qnA.getUpdatedAt()
        );
    }
}
