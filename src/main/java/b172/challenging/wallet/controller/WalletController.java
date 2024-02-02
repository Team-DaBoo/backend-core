package b172.challenging.wallet.controller;

import b172.challenging.wallet.dto.MaterialWalletResponseDto;
import b172.challenging.wallet.dto.WalletResponseDto;
import b172.challenging.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "함께 모으기 API", description = "함께 모으기 (홈 화면) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get Wallet Point" , description = "포인트 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                            content = {@Content(schema = @Schema(implementation = WalletResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
    })
    @Parameter(name = "userId", description = "User Id", example = "user")
    public ResponseEntity<WalletResponseDto> getMyWallet (@PathVariable Long userId){
        return ResponseEntity.ok(walletService.findMyWallet(userId));
    }

    @GetMapping("/material/{userId}")
    @Operation(summary = "Get Material" , description = "재료 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MaterialWalletResponseDto.class))}),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
    })
    @Parameter(name = "userId", description = "User Id", example = "user")
    public ResponseEntity<MaterialWalletResponseDto> getMyMaterialWallet (@PathVariable Long userId){
        return ResponseEntity.ok(walletService.findMyMaterialWallet(userId));
    }
}
