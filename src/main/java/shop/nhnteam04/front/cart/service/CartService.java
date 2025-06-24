package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.nhnteam04.front.cart.dto.UserCartResponse;
import shop.nhnteam04.front.cart.feign.CartFeignClient;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartFeignClient cartFeignClient;
    // 비회원에서 로그인한 순간 세션 장바구니와 회원 장바구니를 병합(프론트에서 로그인성공후 병합 요청)
    public UserCartResponse mergeCarts(Long userId){
        return cartFeignClient.mergeCarts(userId);
    }
}
