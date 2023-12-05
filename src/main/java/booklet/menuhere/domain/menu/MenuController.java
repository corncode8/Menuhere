package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.form.MenuAddDto;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final FileStore fileStore;

    private final MenuService menuService;

    @GetMapping("/addform")
    public String addForm(Model model) {
        model.addAttribute("menu", new MenuAddDto());
        return "form/addform";
    }

    @ModelAttribute("category")
    public Category[] itemTypes() {
//        ItemType[] values = ItemType.values();
//        return values;
        return Category.values(); // 위의 2줄을 한줄로 압축
    }

    @PostMapping("/add")
    public String addMenu(@Validated @ModelAttribute("menu") MenuAddDto form, BindingResult result) throws Exception{
        if (result.hasErrors()) {
            log.info("errors = {}", result);
            return "/menu";
        }

        menuService.addMenu(form);

        return "redirect:/menu";
    }


    @GetMapping("/menu")
    public String menu(Model model) {
        List<MenuViewDto> forms = menuService.viewMenu();

        model.addAttribute("menuList", forms);

        return "menu";
    }

    @GetMapping("/api/role")
    @ResponseBody
    public Map<String, String> role() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String userRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")) ? "ROLE_MANAGER" : null;

        Map<String, String> result = new HashMap<>();
        result.put("userRole", userRole);
        log.info("role-api userRole : {}", userRole);

        return result;
    }


    @ResponseBody
    @GetMapping("/image/storeImage/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
