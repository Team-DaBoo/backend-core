package b172.challenging.wallet.service;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.wallet.domain.MaterialWallet;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.dto.MaterialWalletResponseDto;
import b172.challenging.wallet.dto.WalletResponseDto;
import b172.challenging.wallet.repository.MaterialWalletRepository;
import b172.challenging.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final MaterialWalletRepository materialWalletRepository;
    private final MemberRepository memberRepository;

    public WalletResponseDto findMyWallet (Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        Wallet wallet = walletRepository.findByMember(member)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));

        return WalletResponseDto.builder()
                .id(wallet.getId())
                .member(wallet.getMember())
                .myHome(wallet.getMyHome())
                .myHomeName(wallet.getHomeName())
                .point(wallet.getPoint())
                .saveAmount(wallet.getSaveAmount())
                .homeUpdatedAt(wallet.getHomeUpdatedAt())
                .build();
    }

    public MaterialWalletResponseDto findMyMaterialWallet (Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER));
        List<MaterialWallet> materialWalletList = materialWalletRepository.findByMember(member);
        if(materialWalletList.isEmpty()) {
            throw new CustomRuntimeException(Exceptions.NOT_FOUND_MEMBER);
        }

        return MaterialWalletResponseDto.builder()
                .materialWallet(materialWalletList)
                .build();

    }
}
