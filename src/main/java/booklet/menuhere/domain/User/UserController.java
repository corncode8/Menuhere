package booklet.menuhere.domain.User;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("user") UserSignUpDto user) {
        return "form/addUserForm";
    }
    @PostMapping("/sign-up")
    public String signUp (@Validated @RequestBody UserSignUpDto userSignUpDto, BindingResult result) throws Exception {
        log.info("userSignUpDto : {}", userSignUpDto);
        if(result.hasErrors()){
            return "login";
        }
        userService.singUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/sign-in")
    public String singIn(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "form/login";
    }

    @PostMapping("/sign-in")
    public String login(@Validated @RequestBody LoginForm form, BindingResult result) throws Exception{
        if (result.hasErrors()) {
            return "form/login";
        }
        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "form/login";
        }

        return "로그인 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/api/validate-token")
    @ResponseBody
    public boolean tokenValid(String token) {
        boolean tokenValid = jwtService.isTokenValid(token);

        if (tokenValid) return true;
        else return false;
    }
}
