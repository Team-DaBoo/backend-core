package b172.challenging.admin.dto;

import b172.challenging.common.domain.UseYn;
import b172.challenging.protip.domain.ProTipType;

public record ProTipSearchRequestDto(
        ProTipType proTipType,

        UseYn useYn,
        String searchCriteria,
        String searchQuery
) {
}
