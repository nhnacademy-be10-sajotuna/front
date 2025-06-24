package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.config.FeignConfig;
import shop.nhnteam04.front.cart.dto.GuestCartResponse;

@FeignClient(name = "cart-api", configuration = FeignConfig.class)
@RequestMapping("/api/guest-carts")
public interface GuestCartFeignClient {
    @GetMapping
    GuestCartResponse getGuestCart();
    @DeleteMapping
    void deleteGuestCart();
}
