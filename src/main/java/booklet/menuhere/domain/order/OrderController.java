package booklet.menuhere.domain.order;

import booklet.menuhere.domain.cart.dtos.CartListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/menu/cart/orders")
    public String order(Model model, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return "cart";
        }
        model.addAttribute("payAmount", cartList.cartTotalPrice);

        return "/form/orderForm";
    }





}
