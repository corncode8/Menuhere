package booklet.menuhere.domain.menu.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.service.UserService;
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

    @GetMapping("/api/role")
    public BaseResponse getRole(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            String token = authorizationHeader.replace("Bearer ", "");
            Optional<String> emailOpt = jwtService.extractEmail(token);
            if (emailOpt.isPresent()) {
                Role role = userService.getRole(emailOpt.get());

                Map<String, String> result = new HashMap<>();
                result.put("userRole", role.getKey());

                return new BaseResponse(result);
            } else {
                return new BaseResponse("Email not found");
            }
        }
        return new BaseResponse("Non-Member");
    }

    @GetMapping("/image/storeImage/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    // category api
    //    @GetMapping("/menus/{category}")
//    public BaseResponse MenusByCategory(@PathVariable Category category) {
//        log.info("List<MenuViewDto> : {}", menuService.findCategory(category));
//        List<MenuViewDto> menuViewDtos = menuService.findCategory(category);
//
//        return new BaseResponse(menuViewDtos);
//    }

    // search api
//    @GetMapping("/api/search/menu")
//    public BaseResponse MenuSearch(MenuSearchDto search) {
//        return new BaseResponse(menuService.MenuSearch(search.getSearch()));
//    }


}
