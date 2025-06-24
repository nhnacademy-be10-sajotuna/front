package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.cart.config.FeignConfig;
import shop.nhnteam04.front.cart.dto.UserCartResponse;

@FeignClient(name = "cart-api", configuration = FeignConfig.class)
@RequestMapping("/api/carts")
public interface CartFeignClient {
    @PostMapping("/merge")
    UserCartResponse mergeCarts(@RequestHeader(value = "X-User-Id") Long userId);
}
