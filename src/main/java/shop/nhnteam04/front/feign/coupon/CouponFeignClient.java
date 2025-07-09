package shop.nhnteam04.front.feign.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;

import java.util.List;

@FeignClient(name = "gateway/order-api", contextId = "couponFeignClient")
public interface CouponFeignClient {

    @GetMapping("/api/coupons/users")
    List<UserCouponDetailResponse> getUserCoupons(@RequestHeader("X-User-Id") Long userId);
}
