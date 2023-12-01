package booklet.menuhere.domain.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController {

    // 장바구니 추가 TODO: CartDto 생성시 값 대입
    @PostMapping("/add/cart")
    public CartListDto addCart(CartDto cart, HttpSession session) {

        // 기존 세션에 저장된 장바구니 목록 가져오기
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        cart.totalPriceCalc();
        // 세션에 저장된 장바구니 목록이 없을 시
        if (cartList == null) {
            List<CartDto> newCart = new ArrayList<>();
            newCart.add(cart);
            cartList = new CartListDto(cart.getTotalPrice(), newCart);
        } else { // 세션에 저장된 장바구니 목록이 있다면
            List<CartDto> prevCart = cartList.getCartDto();
            int cartTotal = cartList.getCartTotalPrice();
            cartList.setCartTotalPrice(cartTotal + cart.getTotalPrice());

            // 이미 장바구니에 추가된 메뉴일시
            if (prevCart.contains(cart)) {

                CartDto newCart = prevCart.get(prevCart.indexOf(cart));
                int newAmount = newCart.getAmount() + cart.getAmount();

                newCart.setAmount(newAmount);
                newCart.totalPriceCalc();
                prevCart.set(prevCart.indexOf(cart), newCart);
            } else { // 장바구니에 추가되어 있지 않은 메뉴일때
                prevCart.add(cart);
            }
        }

        session.setAttribute("cartList", cartList);
        log.info("cartList : {}", cartList);

        return cartList;
    }

    @GetMapping("/cart/count")
    public int cntCart(HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        if (cartList == null) {
            return 0;
        } else {
            return cartList.getCartDto().size();
        }
    }

    // 장바구니 목록
    @GetMapping("/menu/cart")
    public CartDto cartList(HttpSession session) {
        CartDto cartList = (CartDto) session.getAttribute("cartList");
        if (cartList == null) {
            return cartList;
        }
        return null;
    }

    // 장바구니 전체 삭제
    @DeleteMapping("/menu/cart/remove")
    public void removeCart(HttpSession session) {
        session.removeAttribute("cartList");
    }

    // 장바구니 1개 삭제
//    @DeleteMapping("/menu/cart/{index}")
//    public CartDTO removeOneCart(@PathVariable int index, HttpSession session) {
//        CartDTO cartList = (CartDTO) session.getAttribute("cartList");
//        if (cartList == null) {
//            return null;
//        }
//        int cartTotal = cartList.getCartTotal();
//        List<Cart> carts = cartList.getCart();
//        int removeCartPrice = carts.get(index).getTotalPrice();
//
//        carts.remove(index);
//        if (carts.size() == 0) {
//            session.removeAttribute("cartList");
//            return null;
//        }
//        cartTotal -= removeCartPrice;
//        cartList.setCartTotal(cartTotal);
//        return cartList;
//    }


}