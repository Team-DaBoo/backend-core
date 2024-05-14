package b172.challenging.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.common.dto.SearchRequestDto;

public interface QnACustomRepository {
	Page<QnA> searchByCriteria(SearchRequestDto searchDto, Pageable pageable);
}
