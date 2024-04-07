package b172.challenging.gathering.repository;

import b172.challenging.admin.dto.GatheringSearchRequestDto;
import b172.challenging.gathering.domain.Gathering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GatheringCustomRepository {
    Page<Gathering> searchByCriteria(GatheringSearchRequestDto searchDto, Pageable pageable);
}
