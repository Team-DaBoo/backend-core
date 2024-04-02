package b172.challenging.qna;

import b172.challenging.member.domain.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record QnAResponseDto(
        List<QnA> qnAList,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last,
        Role role
) {
}
