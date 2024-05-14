package b172.challenging.gathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.gathering.domain.Gathering;

public interface GatheringCustomRepository {
	Page<Gathering> searchByCriteria(GatheringSearchRequestDto searchDto, Pageable pageable);
}
