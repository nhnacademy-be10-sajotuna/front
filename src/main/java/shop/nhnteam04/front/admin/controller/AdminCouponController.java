package shop.nhnteam04.front.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.admin.service.AdminCouponService;
import shop.nhnteam04.front.coupon.dto.request.CouponRequest;

@Slf4j
@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @GetMapping
    public ModelAndView adminCouponPage() {
        ModelAndView mvc = new ModelAndView("admin/coupons");

        // TODO: 쿠폰 목록 가져오기

        return mvc;
    }

    @GetMapping("/create")
    public String createCouponPage() {
        return "admin/coupons-create";
    }

    @PostMapping("/create")
    public String adminCouponPagePost(@Valid @ModelAttribute CouponRequest couponRequest, HttpServletRequest req) {
        String isbn = req.getParameter("isbn");
        String categoryId = req.getParameter("categoryId");

        adminCouponService.createCoupon(couponRequest, isbn, categoryId);

        return "redirect:/admin/coupons";
    }
}
