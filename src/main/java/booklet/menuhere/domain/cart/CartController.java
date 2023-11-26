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

    // 장바구니 추가
//    @PostMapping("/add/cart")
//    public CartDTO addCart(CartDTO cart, HttpSession session) {

//        // 기존 세션에 저장된 장바구니 목록 가져오기
//        CartDTO cartListDTO = (CartDTO) session.getAttribute("cartList");
//
//        cart.totalPriceCalc();
//        // 세션에 저장된 장바구니 목록이 없을 시
//        if (cartListDTO == null) {
//            List<CartDTO> newCart = new ArrayList<>();
//
//        } else { // 세션에 저장된 장바구니 목록이 있다면
//            List<CartDTO> prevCart = cartListDTO.getCart();
//            int cartTotal = cartListDTO.getCartTotal();
//            cartListDTO.setCartTotal(cartTotal + cart.getTotalPrice());
//
//            // 이미 장바구니에 추가된 메뉴일시
//            if (prevCart.contains(cart)) {
//                int cartIdx = prevCart.indexOf(cart);
//                int amount = cart.getAmount();
//
//                Cart newCart = prevCart.get(cartIdx);
//                int newAmount = newCart.getAmount() + amount;
//
//                newCart.setAmount(newAmount);
//                newCart.totalPriceCalc();
//                prevCart.set(cartIdx, newCart);
//            } else {
//                prevCart.add(cart);
//            }
//        }
//
//        session.setAttribute("cartList", cartListDTO);
//        log.info("cartList : {}", cartListDTO);

//        return CartDTO;
//    }

    @GetMapping("/menu/cart/count")
    public int cntCart(HttpSession session) {
        CartDTO cartList = (CartDTO) session.getAttribute("cartList");
        if (cartList == null) {
            return 0;
        }
        return cartList.getCartTotal();
    }

    // 장바구니 목록
    @GetMapping("/menu/cart")
    public CartDTO cartList(HttpSession session) {
        CartDTO cartList = (CartDTO) session.getAttribute("cartList");
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
