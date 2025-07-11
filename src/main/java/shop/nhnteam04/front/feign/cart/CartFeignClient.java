package shop.nhnteam04.front.feign.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.CartItemResponse;
import shop.nhnteam04.front.cart.dto.CartResponse;


// 불필요한거 나중에 지우기
@FeignClient(name = "CART-API")
public interface CartFeignClient {
    @PostMapping("/api/carts/merge")
    CartResponse mergeCarts(@RequestHeader(value = "X-User-Id") Long userId,
                            @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    @GetMapping("/api/user-carts")
    CartResponse getUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    @DeleteMapping("/api/user-carts")
    void deleteUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    @GetMapping("/api/guest-carts")
    CartResponse getGuestCart(@RequestHeader(value = "X-Guest-Cart-Id")String cartId);

    @DeleteMapping("/api/guest-carts")
    void deleteGuestCart(@RequestHeader(value = "X-Guest-Cart-Id")String cartId);

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
