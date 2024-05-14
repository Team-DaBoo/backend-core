package b172.challenging.wallet.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import b172.challenging.wallet.dto.MaterialWalletResponseDto;
import b172.challenging.wallet.dto.WalletResponseDto;
import b172.challenging.wallet.service.WalletService;

@Tag(name = "월렛 API", description = "사용자 월렛(포인트, 나의 집 재료) 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet")
public class WalletController {
	private final WalletService walletService;

	@GetMapping("")
	@Operation(summary = "Get Wallet Point", description = "포인트 정보 가져오기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(schema = @Schema(implementation = WalletResponseDto.class))}),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
	})
	public ResponseEntity<WalletResponseDto> getMyWallet(Principal principal) {
		Long memberId = Long.parseLong(principal.getName());
		return ResponseEntity.ok(walletService.findMyWallet(memberId));
	}

	@GetMapping("/material")
	@Operation(summary = "Get Material", description = "재료 정보 가져오기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(schema = @Schema(implementation = MaterialWalletResponseDto.class))}),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다.")
	})
	public ResponseEntity<List<MaterialWalletResponseDto>> getMyMaterialWallet(Principal principal) {
		Long memberId = Long.parseLong(principal.getName());
		return ResponseEntity.ok(walletService.findMyMaterialWallet(memberId));
	}
}
