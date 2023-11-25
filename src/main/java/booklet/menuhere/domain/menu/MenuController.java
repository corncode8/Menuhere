package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.menu.form.MenuAddForm;
import booklet.menuhere.domain.menu.form.MenuViewForm;
import booklet.menuhere.repository.MenuRepository;
import booklet.menuhere.repository.UploadFileRepository;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;
    private final UploadFileRepository uploadFileRepository;
    private Authentication authentication;


    @Value("${file.dir}")
    private String fileDir;

    private final MenuService menuService;

    @GetMapping("/addform")
    public String addForm(Model model) {
        model.addAttribute("menu", new MenuAddForm());
        return "form/addform";
    }

    @PostMapping("/add")
    public String addMenu(@Validated @ModelAttribute("menu") MenuAddForm form, BindingResult result) throws Exception{
        if (result.hasErrors()) {
            log.info("errors = {}", result);
            return "/menu";
        }

        menuService.addMenu(form);

        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String userRole = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER")) ? "ROLE_MANAGER" : null;

        List<MenuViewForm> forms = menuService.viewMenu();

        model.addAttribute("menus", forms);
        model.addAttribute("userRole", userRole);
        log.info("userRole : {}", userRole);

        return "menu";
    }




}
