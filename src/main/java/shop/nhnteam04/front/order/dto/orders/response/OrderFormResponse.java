package shop.nhnteam04.front.order.dto.orders.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderFormResponse {

    //패키지 정보
    private List<PackageResponse> packages;

    //포인트
    private Integer point;

    //쿠폰
    private List<UserCouponDetailResponse> coupons;

    //배달비
    private DeliveryPriceResponse deliveryPrice;
}

