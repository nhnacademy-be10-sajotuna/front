package shop.nhnteam04.front.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponResponse {
    private Long id;
    private String name;
    private CouponType couponType;
    private CouponPolicyType policyType;
    private Integer discountAmount;
    private Integer minOrderAmount;
    private Integer maxDiscountAmount;
    private Integer validDays;
}
