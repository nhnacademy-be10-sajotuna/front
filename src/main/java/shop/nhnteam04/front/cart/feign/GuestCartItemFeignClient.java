package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.config.FeignConfig;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.GuestCartItemResponse;

@FeignClient(name = "cart-api", configuration = FeignConfig.class)
@RequestMapping("/api/guest-cart-items")
public interface GuestCartItemFeignClient {
    @PostMapping
    GuestCartItemResponse addGuestCartItem(@RequestBody CartItemRequest request);
    @GetMapping("/{bookId}")
    GuestCartItemResponse getGuestCartItem(@PathVariable Long bookId);
    @PatchMapping
    GuestCartItemResponse updateGuestCartItem(@RequestBody CartItemRequest request);
    @DeleteMapping("/{bookId}")
    void deleteGuestCartItem(@PathVariable Long bookId);
    @DeleteMapping
    void clearGuestCartItems();
}
