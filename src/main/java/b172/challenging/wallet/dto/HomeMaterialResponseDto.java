package b172.challenging.wallet.dto;

import b172.challenging.myhome.domain.HomeMaterial;
import b172.challenging.myhome.dto.MyHomeResponseDto;

public record HomeMaterialResponseDto(
	Long id,
	MyHomeResponseDto myHome,
	String name,
	Long needed

) {
	public static HomeMaterialResponseDto from(HomeMaterial homeMaterial) {
		return new HomeMaterialResponseDto(
			homeMaterial.getId(),
			MyHomeResponseDto.from(homeMaterial.getMyHome()),
			homeMaterial.getName(),
			homeMaterial.getNeeded()
		);
	}
}
