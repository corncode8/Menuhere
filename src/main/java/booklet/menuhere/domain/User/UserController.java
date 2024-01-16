package booklet.menuhere.domain.User;

import booklet.menuhere.domain.User.dtos.LoginForm;
import booklet.menuhere.domain.User.dtos.UserSignUpDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.exception.BaseResponseStatus;
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
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/sign-in")
    public String singIn(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "form/login";
    }

    @PostMapping("/sign-in")
    public BaseResponse login(@Validated @RequestBody LoginForm form, BindingResult result) throws Exception{
        if (result.hasErrors()) {
            return new BaseResponse(BaseResponseStatus.LOGIN_EXCEPTION);
        }
        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            return new BaseResponse(BaseResponseStatus.LOGIN_EXCEPTION);
        }

        return new BaseResponse(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }



}
