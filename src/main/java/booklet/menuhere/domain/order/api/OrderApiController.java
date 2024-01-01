package booklet.menuhere.domain.order.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.cart.dtos.CartListDto;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final JwtService jwtService;

    // 결재 금액 Api
    @GetMapping("/order/amount")
    public BaseResponse payAmount(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        return new BaseResponse(cartList.cartTotalPrice);
    }

    @PostMapping("/new/order")
    public BaseResponse MakeOrder(@RequestBody MakeOrderDto makeOrderDto,@RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpSession session) {
        log.info("Received order request: {}", makeOrderDto);

        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        makeOrderDto.setOrderPrice(cartList.getCartTotalPrice());

        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            String token = authorizationHeader.replace("Bearer ", "");

            try {
                Optional<String> emailOpt = jwtService.extractEmail(token);
                String email = emailOpt.get();
                makeOrderDto.setEmail(email);
                log.info("makeOrderDto : {}", makeOrderDto);
                orderService.createOrder(makeOrderDto);
                return new BaseResponse(true);
            } catch (Exception e) {
                return new BaseResponse(e);
            }
        } else {
            try {
                makeOrderDto.setEmail("Non-Members");
                orderService.createOrder(makeOrderDto);
                return new BaseResponse(true);
            } catch (Exception e) {
                return new BaseResponse(e);
            }
        }
    }

}
