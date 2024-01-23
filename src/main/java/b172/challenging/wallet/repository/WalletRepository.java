package b172.challenging.wallet.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByMember (Member member);
}
