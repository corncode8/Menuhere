package booklet.menuhere.controller;

import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.cart.dtos.CartListDto;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.OrderSearch;
import booklet.menuhere.service.MenuService;
import booklet.menuhere.service.OrderService;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/menu/cart/orders")
    public String order(Model model, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return "cart";
        }
        model.addAttribute("payAmount", cartList.cartTotalPrice);

        return "/form/orderForm";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);

        return "/orderList";
    }

}
