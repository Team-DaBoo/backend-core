package b172.challenging.member.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByOauthProviderAndOauthId(OauthProvider oauthProvider, String oauthId);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByNicknameAndIdNot(String nickname, Long id);
}