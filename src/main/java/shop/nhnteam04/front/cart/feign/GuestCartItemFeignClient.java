package shop.nhnteam04.front.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;

@FeignClient(name = "CART-API", contextId = "GuestCartItemFeign")
public interface GuestCartItemFeignClient {
    @PostMapping("/api/guest-cart-items")
    CartItemResponse addGuestCartItem(@RequestBody CartItemRequest request,
                                      @RequestHeader(value = "X-Guest-Cart-Id")String cartId);
    @GetMapping("/api/guest-cart-items/{bookId}")
    CartItemResponse getGuestCartItem(@PathVariable String bookId,
                                      @RequestHeader(value = "X-Guest-Cart-Id")String cartId);
    @PatchMapping("/api/guest-cart-items")
    CartItemResponse updateGuestCartItem(@RequestBody CartItemRequest request,
                                         @RequestHeader(value = "X-Guest-Cart-Id")String cartId);
    @DeleteMapping("/api/guest-cart-items/{bookId}")
    void deleteGuestCartItem(@PathVariable String bookId,
                             @RequestHeader(value = "X-Guest-Cart-Id")String cartId);
    @DeleteMapping("/api/guest-cart-items")
    void clearGuestCartItems(@RequestHeader(value = "X-Guest-Cart-Id")String cartId);
}
