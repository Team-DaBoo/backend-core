package b172.challenging.wallet.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.myhome.domain.MyHome;
import b172.challenging.myhome.service.MyHomeService;
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
    private final MyHomeService myHomeService;

    public WalletResponseDto findMyWallet (Long memberId){
        Optional<Wallet> optionalWallet = walletRepository.findByMemberId(memberId);

        return optionalWallet.map( wallet -> WalletResponseDto.builder()
                .id(wallet.getId())
                .member(wallet.getMember())
                .myHome(wallet.getMyHome())
                .myHomeName(wallet.getHomeName())
                .point(wallet.getPoint())
                .saveAmount(wallet.getSaveAmount())
                .homeUpdatedAt(wallet.getUpdatedAt())
                .build())
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));
    }

    public MaterialWalletResponseDto findMyMaterialWallet (Long memberId){
        List<MaterialWallet> materialWalletList = materialWalletRepository.findByMemberId(memberId);
        if(materialWalletList.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET);
        }

        return MaterialWalletResponseDto
                .builder()
                .materialWallet(materialWalletList)
                .build();

    }

    public void createWallet(Member member) {

        String nickName = member.getNickname();
        MyHome home = myHomeService.findFirstMyhome();
        Wallet wallet = Wallet.builder()
                .homeName(nickName + "의 집")
                .point(0L)
                .saveAmount(0L)
                .member(member)
                .myHome(home)
                .build();
        walletRepository.save(wallet);
    }
}
