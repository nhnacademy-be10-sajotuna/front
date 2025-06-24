package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.GuestCartItemResponse;
import shop.nhnteam04.front.cart.feign.GuestCartItemFeignClient;

@Service
@RequiredArgsConstructor
public class GuestCartItemService {
    private final GuestCartItemFeignClient guestCartItemFeignClient;

    // 비회원 장바구니에 책담기(해당세션의 장바구니가 없을경우 장바구니 생성)
    public GuestCartItemResponse addGuestCartItem(CartItemRequest request) {
        return guestCartItemFeignClient.addGuestCartItem(request);
    }
    // 비회원 장바구니 책 단건조회
    public GuestCartItemResponse getGuestCartItem(Long bookId) {
        return guestCartItemFeignClient.getGuestCartItem(bookId);
    }
    // 비회원 장바구니 책 수량 변경
    public GuestCartItemResponse updateGuestCartItem(CartItemRequest request) {
        return guestCartItemFeignClient.updateGuestCartItem(request);
    }
    // 비회원 장바구니 책 삭제(단건 삭제)
    public void deleteGuestCartItem(Long bookId) {
        guestCartItemFeignClient.deleteGuestCartItem(bookId);
    }
    // 비회원 장바구니 비우기
    public void clearGuestCartItems() {
        guestCartItemFeignClient.clearGuestCartItems();
    }
}
