package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.CartResponse;
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
    // 장바구니 완전삭제(유저가 회원탈퇴할때 카트가 db에 남지 않도록)
    public void deleteUserCart(Long userId) {
        cartFeignClient.deleteUserCart(userId);
    }

    // 자동으로 쿠키(JSESSIONID)를 요청 헤더에 넣어서 보냄
    // 장바구니 조회(비회원 장바구니 조회 - 모든 아이템 조회)
    public CartResponse getGuestCart(String cartId){
        return cartFeignClient.getGuestCart(cartId);
    }

    // 비회원 장바구니 수동삭제(레디스에서 자동삭제되게 하였지만 혹시나 필요할경우 사용)
    public void deleteGuestCart(String cartId){
        cartFeignClient.deleteGuestCart(cartId);
    }
}
