package shop.nhnteam04.front.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;
import shop.nhnteam04.front.order.service.OrderService;
import shop.nhnteam04.front.point.service.PointService;
import shop.nhnteam04.front.review.response.ReviewResponse;
import shop.nhnteam04.front.review.service.ReviewService;
import shop.nhnteam04.front.book.service.LikeService;
import shop.nhnteam04.front.book.dto.response.BookResponse;

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
    private final ReviewService reviewService;
    private final LikeService likeService;

    // 유저 주문 내역
    @GetMapping("/orders")
    public ModelAndView userOrderForm(@AuthenticationPrincipal SecurityUser user,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        // TODO: html 만들기
        ModelAndView mav = new ModelAndView("mypage/orders");

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderInfoResponse> orders = orderService.getUserOrders(user.getId(), pageable);

        mav.addObject("currentPage", orders.getNumber());
        mav.addObject("totalPages", orders.getTotalPages());
        mav.addObject("size", orders.getSize());
        mav.addObject("orders", orders);

        return mav;
    }

    // 마이페이지 메인
    @GetMapping
    public ModelAndView myPageMain(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/main");
        
        if (user != null) {
            // 찜한 책 개수 조회
            List<BookResponse> likedBooks = likeService.getLikedBooks(user.getId());
            mav.addObject("likedBooksCount", likedBooks.size());
            
            // 최근 찜한 책 3개만 가져오기 (대시보드에 표시용)
            List<BookResponse> recentLikedBooks = likedBooks.stream()
                    .limit(3)
                    .toList();
            mav.addObject("recentLikedBooks", recentLikedBooks);
        }
        
        return mav;
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
        
        // 페이지 범위 계산 (5개씩 표시)∑
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

    @GetMapping("/reviews")
    public String myReviews(@AuthenticationPrincipal SecurityUser user, Model model) {
        List<ReviewResponse> reviews = reviewService.getReviewsByUser(user.getId());
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", user);
        return "mypage/review-list";
    }

    // 좋아요 책 목록
    @GetMapping("/liked-books")
    public ModelAndView likedBooks(@AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("mypage/liked-books");
        List<BookResponse> likedBooks = likeService.getLikedBooks(user.getId());
        mav.addObject("likedBooks", likedBooks);
        mav.addObject("user", user);
        return mav;
    }
}
