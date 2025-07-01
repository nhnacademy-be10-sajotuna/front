package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;
import shop.nhnteam04.front.cart.dto.CartResponse;
import shop.nhnteam04.front.cart.dto.CartSummaryResponse;
import shop.nhnteam04.front.cart.service.*;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final UserCartService userCartService;
    private final GuestCartService guestCartService;
    private final UserCartItemService userCartItemService;
    private final GuestCartItemService guestCartItemService;
    private final CartService cartService;

    private final LoginService loginService;

    @ModelAttribute
    public void addUserToModel(@RequestHeader(name = "X-User-Id", required = false)Long userId ,Model model) {
        if (userId != null) {
            ResponseUserWithPolicy responseUserWithPolicy = loginService.me(userId);
            model.addAttribute("user", responseUserWithPolicy);
        }
    }

    // 장바구니 페이지 - 유저아이디가 널이면 비회원 카트 넘겨주고 아니면 그 유저의 카트를 넘겨줌
    @GetMapping
    public String cart(@RequestHeader(name = "X-User-Id", required = false) Long userId,
                       @RequestHeader(value = "X-Guest-Cart-Id", required = false)String cartId, Model model) {
        CartResponse cart = (userId != null)
                ? userCartService.getUserCart(userId)
                : guestCartService.getGuestCart(cartId) ;

        model.addAttribute("cart", cart);
        model.addAttribute("summary", CartSummaryResponse.of(cart));
        return "cart";
    }

    // 장바구니에 책 담기
    @PostMapping
    public String addBookToCart(@RequestHeader(name = "X-User-Id", required = false) Long userId,
                                @ModelAttribute CartItemRequest request,
                                @RequestHeader(value = "X-Guest-Cart-Id",required = false)String cartId,
                                Model model) {
        CartItemResponse response;
        System.out.println(cartId);

        if (userId != null) {
            response = userCartItemService.addUserCartItem(userId, request);
        } else {
            response = guestCartItemService.addGuestCartItem(request, cartId);
        }
        model.addAttribute("message", "장바구니에 담았습니다. 장바구니로 이동하시겠습니까?");
        return "book"; // 원래 페이지 그대로
    }

    // 장바구니 책 단건 삭제
    @PostMapping("/delete")
    public String deleteBookFromCart(@RequestHeader(name = "X-User-Id", required = false) Long userId,
                                     @RequestParam(required = false) String bookId,
                                     @RequestParam(required = false) Long cartItemId,
                                     @RequestHeader(value = "X-Guest-Cart-Id",required = false)String cartId) {
        if (userId != null && cartItemId != null) {
            userCartItemService.deleteUserCartItem(cartItemId);
        } else if (userId == null && bookId != null) {
            guestCartItemService.deleteGuestCartItem(bookId, cartId);
        }

        return "redirect:/cart";
    }

    // 회원 카트 비회원 카트 병합
    @PostMapping("/merge")
    public String mergeCart(@RequestHeader(name = "X-User-Id", required = false) Long userId,
                            @RequestHeader(value = "X-Guest-Cart-Id",required = false) String cartId){
         cartService.mergeCarts(userId, cartId);
        return "redirect:/";
    }
}
