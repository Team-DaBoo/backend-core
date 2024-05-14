package b172.challenging.announcements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.common.dto.SearchRequestDto;

public interface AnnouncementsCustomRepository {
	Page<Announcements> searchByCriteria(SearchRequestDto searchDto, Pageable pageable);
}
