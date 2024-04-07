package b172.challenging.common.dto;

import b172.challenging.common.domain.UseYn;

public record SearchRequestDto(
        UseYn useYn,
        String searchCriteria,
        String searchQuery
) {

}
