package booklet.menuhere.controller.api;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.domain.cart.dtos.CartListDto;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.exception.BaseResponseStatus;
import booklet.menuhere.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(summary = "결재 금액 return Api", description = "세션의 장바구니를 확인하여 총 결제금액 return")
    @GetMapping("/api/order/amount")
    public BaseResponse payAmount(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return new BaseResponse(BaseResponseStatus.EMPTY_CART);
        } else  {
            return new BaseResponse(cartList.cartTotalPrice);
        }
    }

    @Operation(summary = "주문 생성 Api", description = "결제 성공시 주문 생성")
    @PostMapping("/api/new/order")
    public BaseResponse MakeOrder(@RequestBody MakeOrderDto makeOrderDto,@RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpSession session) {
        log.info("Received order request: {}", makeOrderDto);

        if (makeOrderDto.getOrderType().equals("dine-in") && makeOrderDto.getTableNo() == null) {
            return new BaseResponse(BaseResponseStatus.TABLE_NO_NULL_EXCEPTION);
        }

        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList == null) {
            return new BaseResponse(BaseResponseStatus.EMPTY_CART);
        }

        makeOrderDto.setOrderPrice(cartList.getCartTotalPrice());

        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            String token = authorizationHeader.replace("Bearer ", "");

            try {
                Optional<String> emailOpt = jwtService.extractEmail(token);
                String email = emailOpt.get();
                makeOrderDto.setEmail(email);
                log.info("makeOrderDto : {}", makeOrderDto);
                orderService.createOrder(makeOrderDto);
                return new BaseResponse(BaseResponseStatus.SUCCESS);
            } catch (Exception e) {
                return new BaseResponse(BaseResponseStatus.INVALID_TOKEN);
            }
        } else {
            try {
                makeOrderDto.setEmail("Non-Members");
                orderService.createOrder(makeOrderDto);
                return new BaseResponse(BaseResponseStatus.SUCCESS_NON_USER);
            } catch (Exception e) {
                return new BaseResponse(BaseResponseStatus.CREATE_ORDER_EXCEPTION);
            }
        }
    }

}
