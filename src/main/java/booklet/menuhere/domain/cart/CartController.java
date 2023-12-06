package booklet.menuhere.domain.cart;

import booklet.menuhere.domain.cart.form.CartDto;
import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.domain.cart.form.CartViewForm;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final MenuService menuService;

    // 장바구니 추가
    @PostMapping("/add/cart")
    @ResponseBody
    public CartListDto addCart(CartDto cartDto, HttpSession session) {

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

        return cartList;
    }

    // 장바구니 목록
    @GetMapping("/menu/cart")
    public String cartList(HttpSession session, Model model) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");
        List<CartViewForm> cartViewForms = new ArrayList<>();

        if (cartList != null) {
            List<CartDto> cartDtos = cartList.getCartDto();

            for (CartDto cartDto : cartDtos) {
                Menu menu = menuService.getMenuName(cartDto.getMenuName());
                if (menu != null) {
                    CartViewForm cartViewForm = new CartViewForm();
                    cartViewForm.setUploadFile(menu.getUploadFile());
                    cartViewForm.setName(menu.getName());
                    cartViewForm.setPrice(menu.getPrice());
                    cartViewForm.setAmount(cartDto.getAmount());
                    cartViewForms.add(cartViewForm);
                }
            }
        }

        model.addAttribute("cartList", cartList);
        model.addAttribute("cartViewForms", cartViewForms);

        return "cart";
    }

    // 장바구니 수량 변경
    @PutMapping("/update/amount/{menuName}")
    @ResponseBody
    public CartListDto plusAmount(@PathVariable String menuName,int amount, HttpSession session) {
        CartListDto cartList = (CartListDto) session.getAttribute("cartList");

        if (cartList != null) {
            List<CartDto> cartDtos = cartList.getCartDto();
            for (CartDto cartDto : cartDtos) {
                if (cartDto.getMenuName().equals(menuName)) {
                    cartDto.setAmount(amount);
                    cartDto.totalPriceCalc();
                }
            }
        }
        return cartList;
    }

    // 장바구니 전체 삭제
    @DeleteMapping("/menu/cart/remove")
    @ResponseBody
    public void removeCart(HttpSession session) {
        session.removeAttribute("cartList");
    }

    // 장바구니 1개 삭제
//    @DeleteMapping("/menu/cart/{index}")
//    @ResponseBody
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
