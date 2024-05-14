package b172.challenging.auth.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import b172.challenging.auth.service.JwtService;
import b172.challenging.member.domain.Member;
import b172.challenging.member.domain.OauthProvider;
import b172.challenging.member.dto.response.MemberProfileResponseDto;
import b172.challenging.member.dto.response.MemberTokenResponseDto;
import b172.challenging.member.repository.MemberRepository;
import b172.challenging.member.service.MemberNicknameService;

@Tag(name = "Auth", description = "Auth 관련 API")
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class AuthController {
	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final MemberNicknameService memberService;

	@GetMapping("/token/{provider}")
	@Operation(summary = "토큰 발급", description = "AccessToken을 통해 JwtToken을 발급합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(schema = @Schema(implementation = MemberTokenResponseDto.class))}),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
	})
	public ResponseEntity<MemberTokenResponseDto> getToken(@PathVariable OauthProvider provider,
		HttpServletRequest httpServletRequest) {

		String accessToken = httpServletRequest.getHeader(jwtService.getAccessHeader());

		log.info("accessToken: {}", accessToken);
		accessToken = accessToken.replace("Bearer ", "");

		// API User 엔드포인트 URL
		String apiUrl = null;
		if (provider.equals(OauthProvider.GOOGLE)) {
			apiUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
		} else {
			apiUrl = "https://kapi.kakao.com/v2/user/me";
		}
		String oauthId = null;
		try {
			// HTTP GET 요청을 보내기 위한 URL 객체 생성
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			// HTTP 메서드 설정 (GET)
			connection.setRequestMethod("GET");

			// Authorization 헤더 설정
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);

			// 응답 코드 확인
			int responseCode = connection.getResponseCode();
			log.info("HTTP 응답 코드: " + responseCode);

			if (responseCode != 200) {
				log.error(connection.getResponseMessage());
				return ResponseEntity.status(responseCode).body(null);
			}
			// 응답 본문 읽기
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();

			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
			oauthId = jsonObject.get("id").getAsString();

			// 응답 내용 출력
			System.out.println("API 응답: " + response);

			// 연결 해제
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		String finalOauthId = oauthId;
		Member member = memberRepository.findByOauthProviderAndOauthId(provider, oauthId)
			.orElseGet(() -> memberRepository.save(
					Member.builder()
						.oauthProvider(provider)
						.oauthId(finalOauthId)
						.nickname(memberService.getRandomNickname())
						.build()
				)
			);

		MemberTokenResponseDto memberTokenResponseDto = MemberTokenResponseDto.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.role(member.getRole())
			.accessToken(jwtService.createAccessToken(member.getId(), member.getRole()))
			.refreshToken(jwtService.createRefreshToken(member.getId()))
			.oauthId(member.getOauthId())
			.build();

		return ResponseEntity.ok(memberTokenResponseDto);
	}

	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "로그인된 사용자를 로그아웃 시킵니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(schema = @Schema(implementation = MemberProfileResponseDto.class))}),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자 입니다."),
	})
	public ResponseEntity<Void> logout(Principal principal) {
		Long memberId = Long.parseLong(principal.getName());
		jwtService.checkMemberId(memberId);
		jwtService.saveRandomJwtCode(memberId);
		return ResponseEntity.ok().build();
	}
}
