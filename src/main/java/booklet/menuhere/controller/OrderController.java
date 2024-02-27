package booklet.menuhere.controller;


import booklet.menuhere.domain.cart.dtos.CartListDto;


import booklet.menuhere.domain.order.dtos.OrderSearchDto;
import booklet.menuhere.domain.order.dtos.OrderViewDto;
import booklet.menuhere.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/menu/cart/ordersheet")
    public String order(Model model, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return "cart";
        }
        model.addAttribute("payAmount", cartList.cartTotalPrice);

        return "form/orderForm";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearchDto orderSearch, Model model, @PageableDefault(size = 20) Pageable pageable) {

        log.info("orderController orderSearch ; " + orderSearch.toString());
        Page<OrderViewDto> orders = orderService.findOrders(orderSearch, pageable);
        model.addAttribute("orders", orders);

        return "orderList";
    }

}
