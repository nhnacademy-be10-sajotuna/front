package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.UserCartResponse;
import shop.nhnteam04.front.cart.feign.UserCartFeignClient;

@Service
@RequiredArgsConstructor
public class UserCartService {
    private final UserCartFeignClient userCartFeignClient;
    // 장바구니 조회(유저의 장바구니 조회 - 모든 아이템 조회)
    public UserCartResponse getUserCart(Long userId) {
        return userCartFeignClient.getUserCart(userId);
    }
    // 장바구니 완전삭제(유저가 회원탈퇴할때 카트가 db에 남지 않도록)
    public void deleteUserCart(Long userId) {
        userCartFeignClient.deleteUserCart(userId);
    }
}
