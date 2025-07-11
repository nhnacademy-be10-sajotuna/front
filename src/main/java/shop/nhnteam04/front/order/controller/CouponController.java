package shop.nhnteam04.front.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.coupon.dto.response.CouponResponse;
import shop.nhnteam04.front.coupon.service.CouponService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/available-coupons/book")
    public ResponseEntity<List<CouponResponse>> getAvailableBookCoupons(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam String isbn,
            @RequestParam Set<Long> categoryIds) {
        
        List<CouponResponse> availableCoupons = couponService.getAvailableBookCoupons(user.getId(), isbn, categoryIds);
        return ResponseEntity.ok(availableCoupons);
    }
}
