package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.cart.dto.CartResponse;

@FeignClient(name = "CART-API", contextId = "UserCartFeign")
public interface UserCartFeignClient {
    @GetMapping("/api/user-carts")
    CartResponse getUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    @DeleteMapping("/api/user-carts")
    void deleteUserCart(@RequestHeader(value = "X-User-Id") Long userId);
}
