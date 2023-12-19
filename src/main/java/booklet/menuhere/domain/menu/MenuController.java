package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.form.MenuAddDto;
import booklet.menuhere.domain.menu.form.MenuEditDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final FileStore fileStore;

    private final MenuService menuService;

    @GetMapping("/menu/addform")
    public String addForm(Model model) {
        model.addAttribute("menu", new MenuAddDto());
        return "form/addform";
    }

    @GetMapping("/menu/{menuId}/edit")
    public String editForm(@PathVariable Long menuId, Model model) {
        MenuEditDto editDto = menuService.editView(menuId);
        model.addAttribute("menu", editDto);
        model.addAttribute("itemTypes", Arrays.asList(Category.values())); // Category의 모든 값 추가
        return "form/editForm";
    }

    @PostMapping("/menu/{menuId}/edit")
    public String editMenu(@PathVariable Long menuId, MenuEditDto menuEditDto, RedirectAttributes redirectAttributes) throws Exception{
        if (menuService.editMenu(menuEditDto)) {
            redirectAttributes.addFlashAttribute("successMessage", "메뉴를 수정하였습니다.");
        } else {
            redirectAttributes.addFlashAttribute("failMessage", "메뉴를 찾을 수 없습니다. 메뉴 수정에 실패하였습니다.");
        }
        return "redirect:/menu";
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


//    @GetMapping("/menu")
//    public String menu(Model model) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
//            List<MenuViewDto> forms = menuService.managerViewMenu();
//            model.addAttribute("menuList", forms);
//            log.info("매니저 입니다.");
//        } else {
//            List<MenuViewDto> forms = menuService.UserViewMenu();
//            model.addAttribute("menuList", forms);
//            log.info("유저 입니다");
//        }
//
//        return "menu";
//    }

    @GetMapping("/menu")
    public String menu(Model model, HttpSession session) {

        List<MenuViewDto> forms = menuService.UserViewMenu();
        CartListDto cartList = (CartListDto) session.getAttribute("cart");
        model.addAttribute("cartList", cartList);
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

    @PutMapping("/removeMenu")
    public String removeMenu(Long menuId, RedirectAttributes redirectAttributes) {
        if(menuService.removeMenu(menuId)) {
            redirectAttributes.addFlashAttribute("successMessage", "메뉴를 삭제하였습니다.");
        } else {
            redirectAttributes.addFlashAttribute("failMessage", "메뉴를 찾을 수 없습니다. 메뉴 삭제에 실패하였습니다.");
        }
        return "redirect:/menu";
    }


}
