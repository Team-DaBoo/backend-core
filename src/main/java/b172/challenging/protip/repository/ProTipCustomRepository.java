package b172.challenging.protip.repository;

import b172.challenging.admin.dto.ProTipSearchRequestDto;
import b172.challenging.protip.domain.ProTip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProTipCustomRepository {
    Page<ProTip> searchByCriteria(ProTipSearchRequestDto searchDto, Pageable pageable);
}
