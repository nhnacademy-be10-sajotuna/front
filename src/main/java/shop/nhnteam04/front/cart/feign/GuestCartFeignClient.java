package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import shop.nhnteam04.front.cart.config.FeignConfig;
import shop.nhnteam04.front.cart.dto.CartResponse;

@FeignClient(name = "CART-API", contextId = "GuestCartFeign", configuration = FeignConfig.class)
public interface GuestCartFeignClient {
    @GetMapping("/api/guest-carts")
    CartResponse getGuestCart();
    @DeleteMapping("/api/guest-carts")
    void deleteGuestCart();
}
