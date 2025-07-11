package shop.nhnteam04.front.feign.coupon;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.nhnteam04.front.coupon.dto.request.CouponRequest;
import shop.nhnteam04.front.coupon.dto.response.CouponResponse;

@FeignClient(name = "gateway/order-api", contextId = "adminCouponFeignClient")
public interface AdminCouponFeignClient {

    @PostMapping("/api/admin/coupons")
    CouponResponse createCoupon(@Valid @RequestBody CouponRequest couponRequest);

    @PostMapping("/api/admin/coupons/book")
    CouponResponse createBookCoupon(@RequestParam String isbn, @RequestBody @Valid CouponRequest couponRequest);

    @PostMapping("/api/admin/coupons/category")
    CouponResponse createCategoryCoupon(@RequestParam Long categoryId, @RequestBody @Valid CouponRequest couponRequest);
}
