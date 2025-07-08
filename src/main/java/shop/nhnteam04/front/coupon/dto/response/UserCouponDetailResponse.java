package shop.nhnteam04.front.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserCouponDetailResponse {
    private Long userCouponId;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private UserCouponType status;

    // 쿠폰 기본 정보
    private String couponName;
    private CouponType couponType;
    private CouponPolicyType policyType;
    private Integer discountAmount;
    private Integer minOrderAmount;
    private Integer maxDiscountAmount;
}
