package b172.challenging.badge.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BadgeMemberResponseDto(

        Long memberId,


        List<BadgeResponseDto> badges

) {
}
