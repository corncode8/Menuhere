package booklet.menuhere.controller;

import booklet.menuhere.domain.User.UserSignUpDto;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("user") UserSignUpDto user) {
        return "form/addUserForm";
    }
    @PostMapping("/sign-up")
    public String signUp (@RequestBody UserSignUpDto userSignUpDto, BindingResult result) throws Exception {
        log.info("userSignUpDto : {}", userSignUpDto);
        if(result.hasErrors()){
            return "login";
        }
        userService.singUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
