package shop.nhnteam04.front.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {

    private final OrderService orderService;

    // 유저 주문 내역
    @GetMapping("/order-list")
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

    // 주문 상세 정보
    @GetMapping("/detail")
    public ModelAndView OrderDetail(@RequestParam("orderId") long orderId) {
        ModelAndView mav = new ModelAndView("order-detail");

        OrderDetailResponse response = orderService.getOrder(orderId);
        mav.addObject("orderDetail", response);

        return mav;
    }
}
