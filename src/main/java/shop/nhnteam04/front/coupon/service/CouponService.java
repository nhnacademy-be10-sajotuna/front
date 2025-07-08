package shop.nhnteam04.front.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;
import shop.nhnteam04.front.feign.coupon.CouponFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponFeignClient couponFeignClient;

    public List<UserCouponDetailResponse> getUserCoupons(Long userId) {
        return couponFeignClient.getUserCoupons(userId);
    }
}
