package b172.challenging.protip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.admin.dto.ProTipSearchRequestDto;
import b172.challenging.protip.domain.ProTip;

public interface ProTipCustomRepository {
	Page<ProTip> searchByCriteria(ProTipSearchRequestDto searchDto, Pageable pageable);
}
