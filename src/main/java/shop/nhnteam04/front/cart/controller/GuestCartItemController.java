package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.service.GuestCartItemService;

@Controller
@RequiredArgsConstructor
@RequestMapping("guest-cart-items")
public class GuestCartItemController {
    private final GuestCartItemService guestCartItemService;

    // 비회원 장바구니에 책담기(해당세션의 장바구니가 없을경우 장바구니 생성) - 책 리스트 페이지에서 버튼누르면 작동


    // 비회원 장바구니 책 단건조회 - 책 상세 페이지에서 사용

}
