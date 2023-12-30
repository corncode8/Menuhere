package booklet.menuhere.domain.order.api;

import booklet.menuhere.domain.cart.dtos.CartListDto;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.service.OrderService;
import booklet.menuhere.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    // 결재 금액 Api
    @GetMapping("/order/amount")
    public BaseResponse payAmount(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        return new BaseResponse(cartList.cartTotalPrice);
    }

    @PostMapping("/new/order")
    public BaseResponse MakeOrder(@RequestBody MakeOrderDto makeOrderDto, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        makeOrderDto.setOrderPrice(cartList.getCartTotalPrice());
        try {
            orderService.createOrder(makeOrderDto);
            // TODO: Return 설정
            return new BaseResponse(true);
        } catch (Exception e) {
            return new BaseResponse(false);
        }



    }

}
