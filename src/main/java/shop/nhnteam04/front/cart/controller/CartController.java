package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.cart.dto.request.CartItemRequest;
import shop.nhnteam04.front.cart.dto.response.CartItemResponse;
import shop.nhnteam04.front.cart.dto.response.CartResponse;
import shop.nhnteam04.front.cart.dto.response.CartSummaryResponse;
import shop.nhnteam04.front.cart.service.CartItemService;
import shop.nhnteam04.front.cart.service.CartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping
    public String cart(@AuthenticationPrincipal SecurityUser user,
                       @CookieValue(required = false, name = "guestCartId") String cartId, Model model) {


        CartResponse cart = (user != null && user.getId() != null)
                ? cartService.getUserCart(user.getId())
                : cartService.getGuestCart(cartId);

        model.addAttribute("cart", cart);
        model.addAttribute("summary", CartSummaryResponse.of(cart));
        model.addAttribute("user", user);
        return "cart/cart";
    }

    @PostMapping
    public String addBookToCart(@AuthenticationPrincipal SecurityUser user,
                                @CookieValue(required = false, name = "guestCartId") String cartId,
                                @ModelAttribute CartItemRequest request, Model model) {
        CartItemResponse response;

        if (user != null && user.getId() != null) {
            response = cartItemService.addUserCartItem(user.getId(), request);
        } else {
            response = cartItemService.addGuestCartItem(request, cartId);
        }
        model.addAttribute("message", "장바구니에 담았습니다. 장바구니로 이동하시겠습니까?");
        return "book"; // 원래 페이지 그대로
    }

    @PostMapping("/update")
    public String updateBookQuantity(@AuthenticationPrincipal SecurityUser user,
                                     @CookieValue(required = false, name = "guestCartId") String cartId,
                                     @ModelAttribute CartItemRequest request) {
        CartItemResponse response;

        if (user != null && user.getId() != null) {
            response = cartItemService.updateUserCartItem(request.getCartItemId(), request);
        } else {
            response = cartItemService.updateGuestCartItem(request, cartId);
        }
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteBookFromCart(@AuthenticationPrincipal SecurityUser user,
                                     @CookieValue(required = false, name = "guestCartId") String cartId,
                                     @RequestParam String isbn,
                                     @RequestParam(required = false) Long cartItemId
                                     ) {

        if(user != null && cartItemId != null) {
            cartItemService.deleteUserCartItem(cartItemId);
        } else if (isbn != null) {
            cartItemService.deleteGuestCartItem(isbn, cartId);
        }
        return "redirect:/cart";
    }
}
