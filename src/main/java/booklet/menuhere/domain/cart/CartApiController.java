package booklet.menuhere.domain.cart;

import booklet.menuhere.domain.cart.dtos.CartDto;
import booklet.menuhere.domain.cart.dtos.CartListDto;
import booklet.menuhere.exception.BaseResponse;
import booklet.menuhere.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartApiController {

    // 장바구니 추가
    @PostMapping("/add/cart")
    public BaseResponse addCart(@Valid CartDto cartDto, HttpSession session) {

        // 기존 세션에 저장된 장바구니 목록 가져오기
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        cartDto.setAmount(1);
        cartDto.totalPriceCalc();
        // 세션에 저장된 장바구니 목록이 없을 시
        if (cartList == null) {
            List<CartDto> newCart = new ArrayList<>();
            newCart.add(cartDto);
            cartList = new CartListDto(cartDto.getTotalPrice(), newCart);
        } else { // 세션에 저장된 장바구니 목록이 있다면
            List<CartDto> prevCart = cartList.getCartDto();
            int cartTotal = cartList.getCartTotalPrice();
            cartList.setCartTotalPrice(cartTotal + cartDto.getTotalPrice());

            // 이미 장바구니에 추가된 메뉴일시
            if(prevCart.contains(cartDto)) {
                int cartIndex = prevCart.indexOf(cartDto);
                int amount = cartDto.getAmount();

                CartDto newCart = prevCart.get(cartIndex);
                int newAmount = newCart.getAmount() + amount;

                newCart.setAmount(newAmount);
                newCart.totalPriceCalc();
                prevCart.set(cartIndex, newCart);

            } else { // 장바구니에 추가되어 있지 않은 메뉴일때
                prevCart.add(cartDto);
            }
        }
        session.setAttribute("cartList", cartList);
        log.info("cartList : {}", cartList);

        return new BaseResponse(cartList);
    }





    // 장바구니 전체 삭제
    @DeleteMapping("/menu/cart/remove")
    public void removeCart(HttpSession session) {
        session.removeAttribute("cartList");
    }

//    TODO: null 반환값 처리

    // 장바구니 1개 삭제
    @DeleteMapping("/menu/cart/{index}")
    public BaseResponse removeOneCart(@PathVariable int index, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList != null) {
            int cartTotal = cartList.getCartTotalPrice();
            List<CartDto> carts = cartList.getCartDto();

            if (carts.size() > index) { // 유효한 인덱스인지 확인
                int removeCartPrice = carts.get(index).getTotalPrice();

                log.info("remove carts : {}", carts.get(index));
                carts.remove(index);
                if (carts.isEmpty()) {
                    session.removeAttribute("cartList");
                    cartList.setCartTotalPrice(0); // 장바구니가 비어있으면 총 주문금액도 0으로 설정
                    return new BaseResponse(cartList);
                } else {
                    cartTotal -= removeCartPrice;
                    cartList.setCartTotalPrice(cartTotal);
                }

                return new BaseResponse(cartList);
            } else {
                return new BaseResponse(BaseResponseStatus.NOT_FOUND_MENU); // 유효하지 않은 인덱스인 경우
            }
        } else {
            return new BaseResponse(BaseResponseStatus.CART_NULL_EXCEPTION);
        }
    }

    // 장바구니 수량 변경
    @PutMapping("/update/amount/{menuName}")
    public BaseResponse plusAmount(@PathVariable String menuName, @RequestBody @Valid CartDto amount, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList != null) {
            List<CartDto> cartDtos = cartList.getCartDto();

            for (CartDto cartDto : cartDtos) {
                String menuNameInCart = cartDto.getMenuName().trim();
                if (menuNameInCart.equals(menuName.trim())) {
                    cartDto.setAmount(amount.getAmount());
                    cartDto.totalPriceCalc();
                }
            }
            int total = 0;
            for (CartDto cartDto : cartDtos) {
                total += cartDto.getTotalPrice();
            }
            log.info("cart total : {}", total);
            cartList.setCartTotalPrice(total);
        }
        log.info("current cart : {}", cartList);
        return new BaseResponse(cartList);
    }

    @GetMapping("/update/amount/cart")
    public BaseResponse amountCart(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList != null) {
            List<CartDto> cartDtos = cartList.getCartDto();
            int amount = 0;
            for (CartDto cartDto : cartDtos) {
                amount += cartDto.getAmount();
            }
            log.info("cart Amount : {}", amount);

            Map<String, Object> map = new HashMap<>();
            map.put("cartAmount", amount);

            return new BaseResponse(map);
        }
        return new BaseResponse(0);
    }

    @GetMapping("/update/price/cart")
    public BaseResponse UpdatePriceCart(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList != null) {
            log.info("가격 업데이트 cartList : {}", cartList);
            return new BaseResponse(cartList);
        }
        return new BaseResponse(0);
    }
}
