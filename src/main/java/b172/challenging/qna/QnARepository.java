package b172.challenging.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.common.domain.UseYn;

public interface QnARepository extends JpaRepository<QnA, Long>, QnACustomRepository {

	Page<QnA> findByUseYnIs(UseYn useYn, Pageable pageable);
}
