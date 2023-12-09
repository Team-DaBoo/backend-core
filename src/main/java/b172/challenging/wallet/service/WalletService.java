package b172.challenging.wallet.service;

import b172.challenging.wallet.domain.MaterialWallet;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.dto.MaterialWalletResponseDto;
import b172.challenging.wallet.dto.WalletResponseDto;
import b172.challenging.wallet.repository.MaterialWalletRepository;
import b172.challenging.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final MaterialWalletRepository materialWalletRepository;

    public WalletResponseDto findMyWallet (Long id){
        Optional<Wallet> optionalWallet = walletRepository.findByMemberId(id);

        return optionalWallet.map( wallet -> WalletResponseDto.builder()
                .id(wallet.getId())
                .member(wallet.getMember())
                .myHome(wallet.getMyHome())
                .myHomeName(wallet.getHomeName())
                .point(wallet.getPoint())
                .saveAmount(wallet.getSaveAmount())
                .homeUpdatedAt(wallet.getHomeUpdatedAt())
                .build())
                .orElse(null);
    }

    public List<MaterialWalletResponseDto> findMyMaterialWallet (Long id){
        List<MaterialWallet> materialWalletList = materialWalletRepository.findByMemberId(id);


        return materialWalletList.stream()
                .map(MaterialWalletResponseDto::new)
                .toList();
    }
}