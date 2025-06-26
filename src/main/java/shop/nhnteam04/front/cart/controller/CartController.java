package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;
import shop.nhnteam04.front.cart.service.GuestCartItemService;
import shop.nhnteam04.front.cart.service.GuestCartService;
import shop.nhnteam04.front.cart.service.UserCartItemService;
import shop.nhnteam04.front.cart.service.UserCartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {
    private final UserCartService userCartService;
    private final GuestCartService guestCartService;
    private final UserCartItemService userCartItemService;
    private final GuestCartItemService guestCartItemService;

    // 장바구니 페이지 - 유저아이디가 널이면 비회원 카트 넘겨주고 아니면 그 유저의 카트를 넘겨줌
    @GetMapping
    public String cart(@RequestHeader(name = "X-User-Id", required = false) Long userId, Model model) {
        if (userId != null) {
            model.addAttribute("cart", userCartService.getUserCart(userId));
        }else{
            model.addAttribute("cart", guestCartService.getGuestCart());
        }
        return "cart";
    }

    // 장바구니에 책 담기
    @PostMapping
    public String addBookToCart(@RequestHeader(name = "X-User-Id", required = false) Long userId,
                                @ModelAttribute CartItemRequest request, Model model) {
        CartItemResponse response;

        if (userId != null) {
            response = userCartItemService.addUserCartItem(userId, request);
        } else {
            response = guestCartItemService.addGuestCartItem(request);
        }
        model.addAttribute("message", "장바구니에 담았습니다. 장바구니로 이동하시겠습니까?");
        return "books"; // 원래 페이지 그대로
    }
}
