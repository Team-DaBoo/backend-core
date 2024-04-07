package b172.challenging.qna;

import b172.challenging.common.dto.SearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnACustomRepository {
    Page<QnA> searchByCriteria(SearchRequestDto searchDto, Pageable pageable);
}
