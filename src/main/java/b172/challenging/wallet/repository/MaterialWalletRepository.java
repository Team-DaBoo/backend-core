package b172.challenging.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.wallet.domain.MaterialWallet;

public interface MaterialWalletRepository extends JpaRepository<MaterialWallet, Long> {
	List<MaterialWallet> findByMemberId(Long memberId);
}
