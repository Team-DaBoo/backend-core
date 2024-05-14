package b172.challenging.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import b172.challenging.common.entity.UserSample;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/example")
public class ExampleController {
	@Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
	@Parameter(name = "str", description = "2번 반복할 문자열", example = "1234")
	@GetMapping("/returnStr")
	public ResponseEntity<Map<String, Object>> returnStr(@RequestParam String str) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", str + str);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "유저 데이터 가져오기", description = "파라미터로 받은 id 에 해당하는 유저.")
	@Parameter(name = "id", description = "유저 id")
	@GetMapping("/userData/{id}")
	public ResponseEntity<UserSample> getUserData(@PathVariable String id) {
		UserSample us = UserSample
			.builder()
			.email(id)
			.name("유저")
			.password("12345678")
			.phoneNumber("010-1234-1234")
			.build();

		return ResponseEntity.ok(us);
	}

	@GetMapping("/test")
	public ResponseEntity<Map<String, Object>> example() {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "예시 API");
		return ResponseEntity.ok(result);
	}

	@Hidden
	@GetMapping("/ignore")
	public String ignore() {
		return "무시되는 API";
	}
}
