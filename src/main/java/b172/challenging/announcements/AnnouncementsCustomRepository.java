package b172.challenging.announcements;

import b172.challenging.common.dto.SearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementsCustomRepository {
    Page<Announcements> searchByCriteria(SearchRequestDto searchDto, Pageable pageable);
}
