package shop.nhnteam04.front.feign.coupon;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order-api", contextId = "couponFeignClient")
public interface CouponFeignClient {
}
