package booklet.menuhere.controller.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.exception.BaseResponseStatus;
import booklet.menuhere.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final FileStore fileStore;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(summary = "토큰으로 유저의 권한 확인")
    @GetMapping("/api/role")
    public BaseResponse getRole(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            String token = authorizationHeader.replace("Bearer ", "");
            Optional<String> emailOpt = jwtService.extractEmail(token);

            if (emailOpt.isPresent()) {
                Role role = userService.getRole(emailOpt.get());

                if (role == null) {
                    return new BaseResponse(BaseResponseStatus.NOT_FOUND_USER);
                }

                Map<String, String> result = new HashMap<>();
                result.put("userRole", role.getKey());

                return new BaseResponse(result);
            } else {
                return new BaseResponse(BaseResponseStatus.NOT_FOUND_USER);
            }
        }
        return new BaseResponse(BaseResponseStatus.NOT_FOUND_USER);
    }

    @GetMapping("/image/storeImage/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
