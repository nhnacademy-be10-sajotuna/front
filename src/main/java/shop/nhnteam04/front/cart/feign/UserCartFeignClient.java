package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.dto.UserCartResponse;

@FeignClient(name = "cart-api")
@RequestMapping("/api/user-carts")
public interface UserCartFeignClient {
    @GetMapping
    UserCartResponse getUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    @DeleteMapping
    void deleteUserCart(@RequestHeader(value = "X-User-Id") Long userId);
}
