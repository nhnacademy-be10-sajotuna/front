package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.config.FeignConfig;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;

@FeignClient(name = "CART-API", contextId = "GuestCartItemFeign", configuration = FeignConfig.class)
public interface GuestCartItemFeignClient {
    @PostMapping("/api/guest-cart-items")
    CartItemResponse addGuestCartItem(@RequestBody CartItemRequest request);
    @GetMapping("/api/guest-cart-items/{bookId}")
    CartItemResponse getGuestCartItem(@PathVariable Long bookId);
    @PatchMapping("/api/guest-cart-items")
    CartItemResponse updateGuestCartItem(@RequestBody CartItemRequest request);
    @DeleteMapping("/api/guest-cart-items/{bookId}")
    void deleteGuestCartItem(@PathVariable Long bookId);
    @DeleteMapping("/api/guest-cart-items")
    void clearGuestCartItems();
}
