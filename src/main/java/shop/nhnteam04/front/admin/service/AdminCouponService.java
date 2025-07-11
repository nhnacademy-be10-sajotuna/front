package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.coupon.dto.request.CouponRequest;
import shop.nhnteam04.front.coupon.dto.response.CouponType;
import shop.nhnteam04.front.feign.coupon.AdminCouponFeignClient;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponFeignClient adminCouponFeignClient;

    public void createCoupon(CouponRequest couponRequest, String isbn, String categoryId) {
        if(couponRequest.getCouponType().equals(CouponType.ORDER)){
            adminCouponFeignClient.createCoupon(couponRequest);
        } else if(couponRequest.getCouponType().equals(CouponType.CATEGORY)){
            adminCouponFeignClient.createCategoryCoupon(Long.valueOf(categoryId), couponRequest);
        } else {
            adminCouponFeignClient.createBookCoupon(isbn, couponRequest);
        }
    }
}
