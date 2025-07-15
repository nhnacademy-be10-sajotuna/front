package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.request.CartItemRequest;
import shop.nhnteam04.front.cart.dto.response.CartItemResponse;
import shop.nhnteam04.front.feign.cart.CartFeignClient;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartFeignClient cartFeignClient;

    // 장바구니에 책담기(해당 유저의 장바구니가 없을경우 장바구니 생성)
    public CartItemResponse addUserCartItem(Long userId, CartItemRequest request) {
        return cartFeignClient.adduserCartItem(userId, request);
    }
    // 장바구니 책 단건조회(책 상세 조회 페이지)
    public CartItemResponse getUserCartItems(Long userId, Long cartItemId) {
        return cartFeignClient.getUserCartItem(userId, cartItemId);
    }
    // 장바구니 책 수량 변경
    public CartItemResponse updateUserCartItem(Long cartItemId, CartItemRequest request) {
        return cartFeignClient.updateUserCartItem(cartItemId, request);
    }
    // 장바구니 책 삭제(단건 삭제)
    public void deleteUserCartItem(Long cartItemId) {
        cartFeignClient.deleteUserCartItem(cartItemId);
    }
    // 장바구니 비우기
    public void clearUserCartItems(Long userId){
        cartFeignClient.clearUserCartItem(userId);
    }
    // 비회원 장바구니에 책담기(해당세션의 장바구니가 없을경우 장바구니 생성)
    public CartItemResponse addGuestCartItem(CartItemRequest request, String cartId) {
        return cartFeignClient.addGuestCartItem(request,cartId);
    }
    // 비회원 장바구니 책 단건조회
    public CartItemResponse getGuestCartItem(String isbn, String cartId) {
        return cartFeignClient.getGuestCartItem(isbn, cartId);
    }
    // 비회원 장바구니 책 수량 변경
    public CartItemResponse updateGuestCartItem(CartItemRequest request, String cartId) {
        return cartFeignClient.updateGuestCartItem(request,cartId);
    }
    // 비회원 장바구니 책 삭제(단건 삭제)
    public void deleteGuestCartItem(String isbn, String cartId) {
        cartFeignClient.deleteGuestCartItem(isbn, cartId);
    }
    // 비회원 장바구니 비우기
    public void clearGuestCartItems(String cartId) {
        cartFeignClient.clearGuestCartItems(cartId);
    }
}
