package b172.challenging.qna;

import b172.challenging.common.domain.UseYn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Page<QnA> findByUseYnIs(UseYn useYn, Pageable pageable);
}
