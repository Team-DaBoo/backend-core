package b172.challenging.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import b172.challenging.common.exception.CustomRuntimeException;
import b172.challenging.common.exception.Exceptions;
import b172.challenging.member.domain.Member;
import b172.challenging.myhome.domain.HomeMaterial;
import b172.challenging.myhome.domain.MyHome;
import b172.challenging.myhome.service.MyHomeService;
import b172.challenging.wallet.domain.MaterialWallet;
import b172.challenging.wallet.domain.Wallet;
import b172.challenging.wallet.dto.MaterialWalletResponseDto;
import b172.challenging.wallet.dto.WalletResponseDto;
import b172.challenging.wallet.repository.HomeMaterialRepository;
import b172.challenging.wallet.repository.MaterialWalletRepository;
import b172.challenging.wallet.repository.WalletRepository;

@Service
@RequiredArgsConstructor
public class WalletService {
	private final WalletRepository walletRepository;
	private final MaterialWalletRepository materialWalletRepository;
	private final MyHomeService myHomeService;
	private final HomeMaterialRepository homeMaterialRepository;

	public WalletResponseDto findMyWallet(Long memberId) {
		Optional<Wallet> optionalWallet = walletRepository.findByMemberId(memberId);
		if (optionalWallet.isEmpty()) {
			throw new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET);
		}
		return WalletResponseDto.from(optionalWallet.get());
	}

	public List<MaterialWalletResponseDto> findMyMaterialWallet(Long memberId) {
		List<MaterialWallet> materialWalletList = materialWalletRepository.findByMemberId(memberId);
		if (materialWalletList.isEmpty()) {
			throw new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET);
		}
		return materialWalletList.stream().map(MaterialWalletResponseDto::from).toList();
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

		List<HomeMaterial> homeMaterial = homeMaterialRepository.findByMyHome(home);
		homeMaterial.stream().map(
			material -> materialWalletRepository.save(MaterialWallet.builder()
				.member(member)
				.homeMaterial(material)
				.collected(0L)
				.build()
			)
		);

		// To-do create Building Method
	}

	public void updateWallet(Long walletId, Long amount) {
		Wallet wallet = walletRepository.getOrThrow(walletId);
		wallet.savePoint(amount);
		walletRepository.save(wallet);
	}

	public void updateHomeMaterial(Long materialWalletId, Long amount) {
		MaterialWallet materialWallet = materialWalletRepository.findById(materialWalletId)
			.orElseThrow(() -> new CustomRuntimeException(Exceptions.NOT_FOUND_WALLET));
		materialWallet.saveMaterial(amount);
		materialWalletRepository.save(materialWallet);
	}
}
