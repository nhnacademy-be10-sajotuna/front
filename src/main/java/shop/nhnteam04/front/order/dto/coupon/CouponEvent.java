package shop.nhnteam04.front.order.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponEvent {
    private Long userId;
    private Long couponId;
}
