package shop.nhnteam04.front.coupon.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import shop.nhnteam04.front.coupon.dto.response.CouponPolicyType;
import shop.nhnteam04.front.coupon.dto.response.CouponType;

@Data
@AllArgsConstructor
public class CouponRequest {
    @NotBlank
    private String name;

    @NotNull
    private CouponType couponType;

    @NotNull
    private CouponPolicyType policyType;

    @NotNull
    @Min(1)
    private Integer discountAmount;

    @NotNull
    @Min(0)
    private Integer minOrderAmount;

    @NotNull
    @Min(0)
    private Integer maxDiscountAmount;

    @NotNull
    @Min(0)
    private Integer validDays;
}
