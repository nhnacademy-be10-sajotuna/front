package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.cart.dto.CartResponse;

@FeignClient(name = "CART-API", contextId = "GuestCartFeign")
public interface GuestCartFeignClient {
    @GetMapping("/api/guest-carts")
    CartResponse getGuestCart(@RequestHeader(value = "X-Guest-Cart-Id")String cartId);
    @DeleteMapping("/api/guest-carts")
    void deleteGuestCart(@RequestHeader(value = "X-Guest-Cart-Id")String cartId);
}
