package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.cart.dto.CartResponse;

@FeignClient(name = "CART-API")
public interface CartFeignClient {
    @PostMapping("/api/carts/merge")
    CartResponse mergeCarts(@RequestHeader(value = "X-User-Id") Long userId,
                            @RequestHeader(value = "X-Guest-Cart-Id") String cartId);
}
