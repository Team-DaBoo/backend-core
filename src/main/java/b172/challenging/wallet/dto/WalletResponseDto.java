package b172.challenging.wallet.dto;

import b172.challenging.member.dto.MemberResponseDto;
import b172.challenging.myhome.dto.MyHomeResponseDto;
import b172.challenging.wallet.domain.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "사용자 포인트 조회")
public record WalletResponseDto(
        Long id,
        MemberResponseDto member,
        MyHomeResponseDto myHome,
        String myHomeName,
        @Schema(description = "현재 포인트")
        Long point,
        @Schema(description = "전체 모은 양")
        Long saveAmount,
        LocalDateTime updatedAt
) {
        public static WalletResponseDto from(Wallet wallet) {
                return new WalletResponseDto(
                        wallet.getId(),
                        MemberResponseDto.from(wallet.getMember()),
                        MyHomeResponseDto.from(wallet.getMyHome()),
                        wallet.getHomeName(),
                        wallet.getPoint(),
                        wallet.getSaveAmount(),
                        wallet.getUpdatedAt());
        }
}
