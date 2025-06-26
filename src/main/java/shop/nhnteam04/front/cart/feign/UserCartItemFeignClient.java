package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;

@FeignClient(name = "CART-API", contextId = "UserCartItemFeign")
public interface UserCartItemFeignClient {
    @PostMapping("/api/user-cart-items")
    CartItemResponse adduserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                     @RequestBody CartItemRequest request);
    @GetMapping("/api/user-cart-items/{cartItemId}")
    CartItemResponse getUserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                                @PathVariable Long cartItemId);
    @PatchMapping("/api/user-cart-items/{cartItemId}")
    CartItemResponse updateUserCartItem(@PathVariable Long cartItemId,
                                                   @RequestBody CartItemRequest request);
    @DeleteMapping("/api/user-cart-items/{cartItemId}")
    void deleteUserCartItem(@PathVariable Long cartItemId);
    @DeleteMapping("/api/user-cart-items")
    void clearUserCartItem(@RequestHeader(value = "X-User-Id") Long userId);
}
