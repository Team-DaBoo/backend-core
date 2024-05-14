package b172.challenging.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

	default Member getOrThrow(Long memberId) {
		return findById(memberId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
	}

	Optional<Member> findByOauthProviderAndOauthId(OauthProvider oauthProvider, String oauthId);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByNicknameAndIdNot(String nickname, Long id);
}
