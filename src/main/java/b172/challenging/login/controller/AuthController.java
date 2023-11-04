package b172.challenging.login.controller;

import b172.challenging.login.dto.JwtTokenDto;
import b172.challenging.login.dto.MemberRequestDto;
import b172.challenging.login.dto.MemberResponseDto;
import b172.challenging.login.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API Document")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "signup", description = "signup api", tags = {"View"})
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @Operation(summary = "login", description = "login api", tags = {"View"})
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
