package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.CartItemRequest;
import shop.nhnteam04.front.cart.dto.UserCartItemResponse;
import shop.nhnteam04.front.cart.feign.UserCartItemFeignClient;

@Service
@RequiredArgsConstructor
public class UserCartItemService {
    private final UserCartItemFeignClient userCartItemFeignClient;
    // 장바구니에 책담기(해당 유저의 장바구니가 없을경우 장바구니 생성)
    public UserCartItemResponse addUserCartItem(Long userId, CartItemRequest request) {
        return userCartItemFeignClient.adduserCartItem(userId, request);
    }
    // 장바구니 책 단건조회(책 상세 조회 페이지)
    public UserCartItemResponse getUserCartItems(Long userId, Long cartItemId) {
        return userCartItemFeignClient.getUserCartItem(userId, cartItemId);
    }
    // 장바구니 책 수량 변경
    public UserCartItemResponse updateUserCartItem(Long cartItemId, CartItemRequest request) {
        return userCartItemFeignClient.updateUserCartItem(cartItemId, request);
    }
    // 장바구니 책 삭제(단건 삭제)
    public void deleteUserCartItem(Long cartItemId) {
        userCartItemFeignClient.deleteUserCartItem(cartItemId);
    }
    // 장바구니 비우기
    public void clearUserCartItems(Long userId){
        userCartItemFeignClient.clearUserCartItem(userId);
    }
}
