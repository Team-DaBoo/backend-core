package b172.challenging;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import b172.challenging.gathering.controller.GatheringController;
import b172.challenging.gathering.domain.AppTechPlatform;

@SpringBootTest
class ChallengingApplicationTests {

	@Autowired
	private GatheringController gatheringController;

	@DisplayName("gathering endpoint")
	@Test
	void 빈_주입_테스트() {
		assert gatheringController != null;
	}

	@DisplayName("gathering processing")
	@Test
	void 동작_테스트() {
		assert Arrays.equals(Objects.requireNonNull(gatheringController.getPlatform().getBody()).appTechPlatform(),
			AppTechPlatform.values());
	}

}
