package shop.nhnteam04.front.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {
    @GetMapping
    public ModelAndView adminCouponPage() {
        ModelAndView mvc = new ModelAndView("admin/coupons");

        // TODO: 쿠폰 목록 가져오기

        return mvc;
    }
}
