package shop.nhnteam04.front.feign.cart;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.cart.dto.request.CartItemRequest;
import shop.nhnteam04.front.cart.dto.response.CartItemResponse;
import shop.nhnteam04.front.cart.dto.response.CartResponse;

@FeignClient(name = "gateway/cart-api")
public interface CartFeignClient {

    /**
     * 비회원에서 로그인한 순간 세션 장바구니와 회원 장바구니를 병합
     * 프론트에서 로그인 성공 후 병합 요청 시 사용
     *
     * @param userId 사용자 ID
     * @param cartId 비회원 장바구니 ID
     * @return 병합된 사용자 장바구니 정보
     */
    @PostMapping("/api/carts/merge")
    CartResponse mergeCarts(@RequestHeader(value = "X-User-Id") Long userId,
                                            @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 조회
     * 카트 ID 기반으로 비회원 장바구니의 모든 아이템 조회
     *
     * @param cartId 비회원 장바구니 ID
     * @return 비회원 장바구니 정보
     */
    @GetMapping("/api/guest-carts")
    CartResponse getGuestCart(@RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 수동 삭제
     * 레디스에서 자동삭제되지만 필요한 경우 수동 삭제 가능
     *
     * @param cartId 삭제할 비회원 장바구니 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/guest-carts")
    Void deleteGuestCart(@RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니에 책 담기
     * 해당 카트 ID의 장바구니가 없을 경우 장바구니 생성
     *
     * @param request 장바구니 아이템 요청 정보
     * @param cartId 비회원 장바구니 ID
     * @return 생성된 비회원 장바구니 아이템 정보
     */
    @PostMapping("/api/guest-cart-items")
    CartItemResponse addGuestCartItem(@Valid @RequestBody CartItemRequest request,
                                                      @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 책 단건 조회
     *
     * @param bookId 책 ISBN
     * @param cartId 비회원 장바구니 ID
     * @return 비회원 장바구니 아이템 정보
     */
    @GetMapping("/api/guest-cart-items/{bookId}")
    CartItemResponse getGuestCartItem(@PathVariable String bookId,
                                                      @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 책 수량 변경
     *
     * @param request 수량 변경 요청 정보
     * @param cartId 비회원 장바구니 ID
     * @return 수정된 비회원 장바구니 아이템 정보
     */
    @PatchMapping("/api/guest-cart-items")
    CartItemResponse updateGuestCartItem(@Valid @RequestBody CartItemRequest request,
                                                         @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 책 삭제 (단건 삭제)
     *
     * @param bookId 삭제할 책 ISBN
     * @param cartId 비회원 장바구니 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/guest-cart-items/{bookId}")
    Void deleteGuestCartItem(@PathVariable String bookId,
                                             @RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 비회원 장바구니 비우기
     * 비회원 장바구니의 모든 아이템 삭제
     *
     * @param cartId 비회원 장바구니 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/guest-cart-items")
    Void clearGuestCartItems(@RequestHeader(value = "X-Guest-Cart-Id") String cartId);

    /**
     * 사용자 장바구니 조회
     * 사용자의 장바구니 모든 아이템 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 장바구니 정보
     */
    @GetMapping("/api/user-carts")
    CartResponse getUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    /**
     * 사용자 장바구니 완전 삭제
     * 사용자가 회원 탈퇴할 때 카트가 DB에 남지 않도록 삭제
     *
     * @param userId 사용자 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/user-carts")
    Void deleteUserCart(@RequestHeader(value = "X-User-Id") Long userId);

    /**
     * 사용자 장바구니에 책 담기
     * 해당 사용자의 장바구니가 없을 경우 장바구니 생성
     *
     * @param userId 사용자 ID
     * @param request 장바구니 아이템 요청 정보
     * @return 생성된 사용자 장바구니 아이템 정보
     */
    @PostMapping("/api/user-cart-items")
    CartItemResponse addUserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                                     @Valid @RequestBody CartItemRequest request);

    /**
     * 사용자 장바구니 책 단건 조회
     *
     * @param userId 사용자 ID
     * @param cartItemId 장바구니 아이템 ID
     * @return 사용자 장바구니 아이템 정보
     */
    @GetMapping("/api/user-cart-items/{cartItemId}")
    CartItemResponse getUserCartItem(@RequestHeader(value = "X-User-Id") Long userId,
                                                     @PathVariable Long cartItemId);

    /**
     * 사용자 장바구니 책 수량 변경
     *
     * @param cartItemId 장바구니 아이템 ID
     * @param request 수량 변경 요청 정보
     * @return 수정된 사용자 장바구니 아이템 정보
     */
    @PatchMapping("/api/user-cart-items/{cartItemId}")
    CartItemResponse updateUserCartItem(@PathVariable Long cartItemId,
                                                        @Valid @RequestBody CartItemRequest request);

    /**
     * 사용자 장바구니 책 삭제 (단건 삭제)
     *
     * @param cartItemId 삭제할 장바구니 아이템 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/user-cart-items/{cartItemId}")
    Void deleteUserCartItem(@PathVariable Long cartItemId);

    /**
     * 사용자 장바구니 비우기
     * 사용자 장바구니의 모든 아이템 삭제
     *
     * @param userId 사용자 ID
     * @return 빈 응답
     */
    @DeleteMapping("/api/user-cart-items")
    Void clearUserCartItems(@RequestHeader(value = "X-User-Id") Long userId);
}