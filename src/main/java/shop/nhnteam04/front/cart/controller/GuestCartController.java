package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.service.GuestCartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("guest-carts")
public class GuestCartController {
    private final GuestCartService guestCartService;

    // 장바구니 조회(비회원 장바구니 조회 - 모든 아이템 조회)
    @GetMapping
    public String getGuestCart(Model model) {
        model.addAttribute("cart", guestCartService.getGuestCart());
        return "cart";
    }
}
