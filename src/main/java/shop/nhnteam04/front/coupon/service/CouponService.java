package shop.nhnteam04.front.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.coupon.dto.response.CouponResponse;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;
import shop.nhnteam04.front.feign.coupon.CouponFeignClient;
import shop.nhnteam04.front.order.dto.coupon.CouponEvent;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponFeignClient couponFeignClient;

    public List<UserCouponDetailResponse> getUserCoupons(Long userId) {
        return couponFeignClient.getUserCoupons(userId);
    }

    public List<CouponResponse> getAvailableBookCoupons(Long userId, String isbn, Set<Long> categoryIds) {
        try {
            return couponFeignClient.getAvailableBookCoupons(userId, isbn, categoryIds);
        } catch (Exception e) {
            log.error("Failed to get available book coupons for user: {}, isbn: {}, categoryIds: {}", userId, isbn, categoryIds, e);
            return List.of(); // 빈 리스트 반환
        }
    }

    public List<CouponResponse> getBookCoupons(String isbn, Set<Long> categoryIds) {
        try {
            return couponFeignClient.getBookCoupons(isbn, categoryIds);
        } catch (Exception e) {
            log.error("Failed to get available book coupons for isbn: {}, categoryIds: {}", isbn, categoryIds, e);
            return List.of(); // 빈 리스트 반환
        }
    }

    public void getBookCoupon(CouponEvent couponEvent) {
        couponFeignClient.userGetBookCoupon(couponEvent);
    }
}
