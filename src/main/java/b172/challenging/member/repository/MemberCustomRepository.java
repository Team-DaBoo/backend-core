package b172.challenging.member.repository;

import b172.challenging.admin.dto.MemberSearchRequestDto;
import b172.challenging.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberCustomRepository {

    Optional<String> findJwtCodeById(Long id);

    Long updateJwtCodeById(Long memberId, String jwtCode);

    Page<Member> searchByCriteria(MemberSearchRequestDto searchDto, Pageable pageable);


}
