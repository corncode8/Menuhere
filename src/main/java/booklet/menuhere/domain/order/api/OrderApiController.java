package booklet.menuhere.domain.order.api;

import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    @GetMapping("/order/amount")
    @ResponseBody
    public BaseResponse payAmount(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        return new BaseResponse(cartList.cartTotalPrice);
    }
}
