package b172.challenging.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.wallet.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	default Wallet getOrThrow(Long walletId) {
		return findById(walletId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));
	}

	Optional<Wallet> findByMemberId(Long memberId);
}
