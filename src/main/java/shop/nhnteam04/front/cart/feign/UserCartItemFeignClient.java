package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.UserCartItemResponse;

@FeignClient(name = "cart-api")
@RequestMapping("/api/user-cart-items")
public interface UserCartItemFeignClient {
    @PostMapping
    UserCartItemResponse adduserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                                @RequestBody CartItemRequest request);
    @GetMapping("/{cartItemId}")
    UserCartItemResponse getUserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                                @PathVariable Long cartItemId);
    @PatchMapping("/{cartItemId}")
    UserCartItemResponse updateUserCartItem(@PathVariable Long cartItemId,
                                                   @RequestBody CartItemRequest request);
    @DeleteMapping("/{cartItemId}")
    void deleteUserCartItem(@PathVariable Long cartItemId);
    @DeleteMapping
    void clearUserCartItem(@RequestHeader(value = "X-User-Id") Long userId);
}
