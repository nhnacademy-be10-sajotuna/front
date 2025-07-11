package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.coupon.dto.request.CouponRequest;
import shop.nhnteam04.front.coupon.dto.response.CouponResponse;
import shop.nhnteam04.front.coupon.dto.response.CouponType;
import shop.nhnteam04.front.feign.coupon.AdminCouponFeignClient;
import shop.nhnteam04.front.feign.coupon.CouponFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponFeignClient adminCouponFeignClient;
    private final CouponFeignClient couponFeignClient;

    public List<CouponResponse> getAllCoupons(){
        return adminCouponFeignClient.getAllCoupons();
    }

    public void createCoupon(CouponRequest couponRequest, String isbn, String categoryId) {
        if(couponRequest.getCouponType().equals(CouponType.ORDER)){
            adminCouponFeignClient.createCoupon(couponRequest);
        } else if(couponRequest.getCouponType().equals(CouponType.CATEGORY)){
            adminCouponFeignClient.createCategoryCoupon(Long.valueOf(categoryId), couponRequest);
        } else {
            adminCouponFeignClient.createBookCoupon(isbn, couponRequest);
        }
    }

    public CouponResponse getCouponById(Long couponId) {
        if(couponId == null || couponId <= 0) {
            throw new IllegalArgumentException();
        }
        return couponFeignClient.getCouponById(couponId);
    }

    public void editCoupon(Long couponId, CouponRequest couponRequest) {
        if(couponId == null || couponId <= 0) {
            throw new IllegalArgumentException();
        }
        adminCouponFeignClient.updateCoupon(couponId, couponRequest);
    }

    public void deleteCoupon(Long couponId) {
        if(couponId == null || couponId <= 0) {
            throw new IllegalArgumentException();
        }
        adminCouponFeignClient.deleteCouponById(couponId);
    }
}
