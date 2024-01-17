package booklet.menuhere.domain.User.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.order.dtos.OrderUserInfoDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.exception.BaseResponseStatus;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("/api/validate-token")
    public BaseResponse tokenValid(String token) {
        boolean tokenValid = jwtService.isTokenValid(token);

        if (tokenValid) return new BaseResponse(true) ;
        else return new BaseResponse(false);
    }

    // 유저 Details Api
    @GetMapping("/api/user/details")
    public BaseResponse getUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Optional<String> emailOpt = jwtService.extractEmail(token);
        if (emailOpt.isPresent()) {
            String email = emailOpt.get();
            OrderUserInfoDto orderUserInfoDto = userService.OrderUserInfo(email);
            log.info("orderUserInfoDto : {}", orderUserInfoDto);
            return new BaseResponse(orderUserInfoDto);
        } else {
            return new BaseResponse(BaseResponseStatus.INVALID_TOKEN);
        }
    }

}
