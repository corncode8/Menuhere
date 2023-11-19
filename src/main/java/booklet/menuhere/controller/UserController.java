package booklet.menuhere.controller;

import booklet.menuhere.domain.User.UserSignUpDto;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signUp (@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.singUp(userSignUpDto);
        return "회원가입 성공";
    }
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
