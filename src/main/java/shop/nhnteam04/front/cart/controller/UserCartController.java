package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.dto.UserCartResponse;
import shop.nhnteam04.front.cart.service.UserCartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("user-carts")
public class UserCartController {
    private final UserCartService userCartService;

    // 장바구니 조회(유저의 장바구니 조회 - 모든 아이템 조회)
    @GetMapping
    public String getCart(@RequestHeader(name = "X-User-Id") Long userId, Model model){
        model.addAttribute("cart", userCartService.getUserCart(userId));
        return "cart";
    }
}
