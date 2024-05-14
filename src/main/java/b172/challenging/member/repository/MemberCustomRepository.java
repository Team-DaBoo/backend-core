package b172.challenging.member.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import b172.challenging.admin.dto.MemberSearchRequestDto;
import b172.challenging.member.domain.Member;

public interface MemberCustomRepository {

	Optional<String> findJwtCodeById(Long id);

	Long updateJwtCodeById(Long memberId, String jwtCode);

	Page<Member> searchByCriteria(MemberSearchRequestDto searchDto, Pageable pageable);

}
