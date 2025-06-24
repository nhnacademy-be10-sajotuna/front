package shop.nhnteam04.front.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.cart.dto.GuestCartResponse;
import shop.nhnteam04.front.cart.feign.GuestCartFeignClient;

@Service
@RequiredArgsConstructor
public class GuestCartService {
    private final GuestCartFeignClient guestCartFeignClient;

    // 자동으로 쿠키(JSESSIONID)를 요청 헤더에 넣어서 보냄
    // 장바구니 조회(비회원 장바구니 조회 - 모든 아이템 조회)
    public GuestCartResponse getGuestCart(){
        return guestCartFeignClient.getGuestCart();
    }

    // 비회원 장바구니 수동삭제(레디스에서 자동삭제되게 하였지만 혹시나 필요할경우 사용)
    public void deleteGuestCart(){
        guestCartFeignClient.deleteGuestCart();
    }
}
