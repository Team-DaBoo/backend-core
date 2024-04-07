package b172.challenging.announcements;

import b172.challenging.common.domain.UseYn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementsRepository extends JpaRepository<Announcements, Long>, AnnouncementsCustomRepository {

    Page<Announcements> findByUseYnIs(UseYn useYn, Pageable pageable);

}
