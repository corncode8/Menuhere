package booklet.menuhere.domain.menu.api;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final FileStore fileStore;

    @GetMapping("/api/role")
    public BaseResponse role() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String userRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")) ? "ROLE_MANAGER" : null;

        Map<String, String> result = new HashMap<>();
        result.put("userRole", userRole);
        log.info("role-api userRole : {}", userRole);

        return new BaseResponse(result);
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
