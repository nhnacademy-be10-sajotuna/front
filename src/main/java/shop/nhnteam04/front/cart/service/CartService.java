package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.response.CartResponse;
import shop.nhnteam04.front.feign.cart.CartFeignClient;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartFeignClient cartFeignClient;

    // 비회원에서 로그인한 순간 세션 장바구니와 회원 장바구니를 병합(프론트에서 로그인성공후 병합 요청)
    public CartResponse mergeCarts(Long userId, String cartId){
        return cartFeignClient.mergeCarts(userId, cartId);
    }

    // 장바구니 조회(유저의 장바구니 조회 - 모든 아이템 조회)
    public CartResponse getUserCart(Long userId) {
        return cartFeignClient.getUserCart(userId);
    }
    // 장바구니 조회(비회원 장바구니 조회 - 모든 아이템 조회)
    public CartResponse getGuestCart(String cartId){
        return cartFeignClient.getGuestCart(cartId);
    }
}
