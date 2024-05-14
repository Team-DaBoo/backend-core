package b172.challenging.protip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.common.domain.UseYn;
import b172.challenging.protip.domain.ProTip;
import b172.challenging.protip.domain.ProTipType;

public interface ProTipRepository extends JpaRepository<ProTip, Long>, ProTipCustomRepository {
	Page<ProTip> findByUseYnIs(UseYn useYn, Pageable page);

	Page<ProTip> findByProTipTypeAndUseYnIs(ProTipType proTipType, UseYn useYn, Pageable page);

	Page<ProTip> findByProTipType(ProTipType proTipType, Pageable page);
}
