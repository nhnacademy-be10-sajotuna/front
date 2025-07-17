package shop.nhnteam04.front.feign.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import shop.nhnteam04.front.coupon.dto.response.CouponResponse;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;

import java.util.List;
import java.util.Set;

@FeignClient(name = "gateway/order-api", contextId = "couponFeignClient")
public interface CouponFeignClient {

    @GetMapping("/api/coupons/users")
    List<UserCouponDetailResponse> getUserCoupons(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/coupons/available-coupons/book")
    List<CouponResponse> getAvailableBookCoupons(@RequestHeader("X-User-Id") Long userId,
                                                @RequestParam("isbn") String isbn,
                                                @RequestParam("categoryIds") Set<Long> categoryIds);

    @GetMapping("/api/coupons/{coupon-id}")
    CouponResponse getCouponById(@PathVariable(name = "coupon-id") Long couponId);


    @GetMapping("/api/coupons/book-coupons")
    List<CouponResponse> getBookCoupons(@RequestParam("isbn") String isbn,
                                        @RequestParam("categoryIds") Set<Long> categoryIds);
}
