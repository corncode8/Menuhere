package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.cart.form.CartDto;
import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.domain.cart.form.CartViewForm;
import booklet.menuhere.domain.menu.form.MenuAddDto;
import booklet.menuhere.domain.menu.form.MenuEditDto;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 장바구니 목록
    @GetMapping("/menu/cart")
    public String cartList(HttpSession session, Model model) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        List<CartViewForm> cartViewForms = new ArrayList<>();

        if (cartList != null) {
            List<CartDto> cartDtos = cartList.getCartDto();

            for (CartDto cartDto : cartDtos) {
                Menu menu = menuService.getMenuName(cartDto.getMenuName());
                if (menu != null) {
                    CartViewForm cartViewForm = new CartViewForm();
                    cartViewForm.setUploadFile(menu.getUploadFile());
                    cartViewForm.setName(menu.getName());
                    cartViewForm.setPrice(menu.getPrice());
                    cartViewForm.setAmount(cartDto.getAmount());
                    cartViewForm.setMenuId(menu.getId());
                    cartViewForms.add(cartViewForm);
                }
            }
        }

        model.addAttribute("cartList", cartList);
        model.addAttribute("cartViewForms", cartViewForms);

        return "cart";
    }

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
    public String editMenu(@PathVariable Long menuId, @Valid MenuEditDto menuEditDto, RedirectAttributes redirectAttributes) throws Exception{
        if (menuService.editMenu(menuEditDto, menuId)) {
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
    public String addMenu(@Validated @ModelAttribute("menu") @Valid MenuAddDto form, BindingResult result) throws Exception{
        if (result.hasErrors()) {
            log.info("errors = {}", result);
            return "/menu";
        }

        menuService.addMenu(form);

        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu(Model model, HttpSession session) {

        List<MenuViewDto> forms = menuService.ViewMenu();
        CartListDto cartList = (CartListDto) session.getAttribute("cart");
        model.addAttribute("cartList", cartList);
        model.addAttribute("menuList", forms);

        return "menu";
    }





}
