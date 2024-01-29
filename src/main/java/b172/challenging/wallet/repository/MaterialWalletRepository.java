package b172.challenging.wallet.repository;

import b172.challenging.member.domain.Member;
import b172.challenging.wallet.domain.MaterialWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialWalletRepository extends JpaRepository<MaterialWallet, Long> {
    List<MaterialWallet> findByMemberId(Long memberId);
}
