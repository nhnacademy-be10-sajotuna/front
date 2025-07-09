package shop.nhnteam04.front.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.account.address.dto.AddressForm;
import shop.nhnteam04.front.account.service.AddressService;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.account.user.request.EditRequestUser;
import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;
import shop.nhnteam04.front.coupon.dto.response.UserCouponDetailResponse;
import shop.nhnteam04.front.coupon.service.CouponService;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;
import shop.nhnteam04.front.order.service.OrderService;
import shop.nhnteam04.front.point.service.PointService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {

    private final OrderService orderService;
    private final PointService pointService;
    private final LoginService loginService;
    private final AddressService addressService;
    private final CouponService couponService;

    // 유저 주문 내역
    @GetMapping("/orders")
    public ModelAndView userOrderForm(@AuthenticationPrincipal SecurityUser user,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        // TODO: html 만들기
        ModelAndView mav = new ModelAndView("userOrderList");

        Pageable pageable = PageRequest.of(page, size);
        List<OrderInfoResponse> list = orderService.getUserOrders(user.getId(), pageable);
        mav.addObject("orders", list);

        return mav;
    }

    // 마이페이지 메인
    @GetMapping
    public ModelAndView myPageMain() {
        return new ModelAndView("mypage/main");
    }

    // 포인트 적립/사용 내역
    @GetMapping("/point-history")
    public ModelAndView pointHistory(@AuthenticationPrincipal SecurityUser user, Pageable pageable) {
        ModelAndView mav = new ModelAndView("mypage/point-history");
        
        Page<PointHistoryResponse> pointHistoryPage = pointService.getPointHistory(user.getId(), pageable);
        int availablePoint = pointService.getAvailablePoint(user.getId());
        mav.addObject("pointHistory", pointHistoryPage.getContent());
        mav.addObject("availablePoint", availablePoint);
        
        // 페이징 정보 추가
        mav.addObject("currentPage", pointHistoryPage.getNumber());
        mav.addObject("totalPages", pointHistoryPage.getTotalPages());
        mav.addObject("size", pointHistoryPage.getSize());
        
        // 페이지 범위 계산 (5개씩 표시)
        int startPage = Math.max(0, pointHistoryPage.getNumber() - 2);
        int endPage = Math.min(pointHistoryPage.getTotalPages() - 1, pointHistoryPage.getNumber() + 2);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", endPage);
        
        return mav;
    }

    // 내 쿠폰 관리
    @GetMapping("/coupons")
    public ModelAndView myCoupons(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/coupons");
        
        // TODO: 쿠폰 서비스 구현 필요
         List<UserCouponDetailResponse> myCoupons = couponService.getUserCoupons(user.getId());
         mav.addObject("coupons", myCoupons);
        
        return mav;
    }

    // 회원 정보 조회
    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/profile");
        ResponseUserWithPolicy responseUserWithPolicy = loginService.detail(user.getId());
        mav.addObject("user", responseUserWithPolicy);
        return mav;
    }

    // 회원 정보 수정 페이지
    @GetMapping("/profile/edit")
    public ModelAndView profileEdit(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/profile-edit");
        ResponseUserWithPolicy responseUserWithPolicy = loginService.detail(user.getId());
        mav.addObject("user", responseUserWithPolicy);
        return mav;
    }

    @PostMapping("/profile/edit")
    public String edit(@AuthenticationPrincipal SecurityUser user, @Valid @ModelAttribute EditRequestUser editRequestUser, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            loginService.updateUser(user.getId(), editRequestUser);
            return "redirect:/my-page/profile";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/my-page/profile/edit?errorMessage="+errorMessage;
        }
    }

    // 주소 관리
    @GetMapping("/address")
    public ModelAndView address(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/address");
        List<AddressForm> addressForms = addressService.getAddresses(user.getId());
        mav.addObject("addressList", addressForms);
        return mav;
    }

    @PostMapping("/address")
    public String createAddress(@AuthenticationPrincipal SecurityUser user, @Valid @ModelAttribute AddressForm address, RedirectAttributes redirectAttributes) {
        try {
            addressService.createAddress(user.getId(), address);
            return "redirect:/my-page/address";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/my-page/address";
        }
    }

    @PostMapping("/address/{addressId}")
    public String deleteAddress(@AuthenticationPrincipal SecurityUser user, @PathVariable Long addressId) {
        addressService.deleteAddress(user.getId(), addressId);
        return "redirect:/my-page/address";
    }
}
