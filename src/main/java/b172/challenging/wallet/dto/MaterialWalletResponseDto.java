package b172.challenging.wallet.dto;

import b172.challenging.member.dto.MemberResponseDto;
import b172.challenging.wallet.domain.MaterialWallet;

public record MaterialWalletResponseDto(
        Long id,
        MemberResponseDto member,
        HomeMaterialResponseDto homeMaterial,
        Long collected
) {
    public static MaterialWalletResponseDto from(MaterialWallet materialWallet) {
        return new MaterialWalletResponseDto(
                materialWallet.getId(),
                MemberResponseDto.from(materialWallet.getMember()),
                HomeMaterialResponseDto.from(materialWallet.getHomeMaterial()),
                materialWallet.getCollected()
        );
    }
}
