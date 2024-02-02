package b172.challenging.wallet.repository;

import b172.challenging.wallet.domain.MaterialWallet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialWalletRepository extends JpaRepository<MaterialWallet, Long> {
    @EntityGraph(attributePaths = {"member","homeMaterial"}, type = EntityGraph.EntityGraphType.FETCH)
    List<MaterialWallet> findByMemberId(Long id);
}