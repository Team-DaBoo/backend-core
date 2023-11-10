package b172.challenging.login.controller;

import b172.challenging.login.dto.UserSignUpDto;
import b172.challenging.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API Document")
public class UserController {

    private final UserService userService;

    @Operation(summary = "signup", description = "signup api")
    @PostMapping("/sign-up")
    public String signup(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @Operation(summary = "login", description = "login api")
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
