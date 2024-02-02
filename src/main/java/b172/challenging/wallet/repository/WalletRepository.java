package b172.challenging.wallet.repository;

import b172.challenging.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @EntityGraph(attributePaths = {"member","myHome"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Wallet> findByMemberId (Long id);
}