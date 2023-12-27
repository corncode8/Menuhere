package booklet.menuhere.domain.User.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final JwtService jwtService;

    @GetMapping("/api/validate-token")
    public BaseResponse tokenValid(String token) {
        boolean tokenValid = jwtService.isTokenValid(token);

        if (tokenValid) return new BaseResponse(true) ;
        else return new BaseResponse(false);
    }
}
