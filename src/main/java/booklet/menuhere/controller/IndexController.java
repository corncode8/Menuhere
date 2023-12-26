package booklet.menuhere.controller;

import booklet.menuhere.domain.cart.form.CartDto;
import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.domain.cart.form.CartViewForm;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MenuService menuService;

    @GetMapping("/")
    public String mainpage() {
        return "main";
    }

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


}
