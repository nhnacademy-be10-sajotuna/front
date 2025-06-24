package shop.nhnteam04.front.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.service.UserCartItemService;

@Controller
@RequiredArgsConstructor
@RequestMapping("user-cart-items")
public class UserCartItemController {
    private final UserCartItemService userCartItemService;


}
