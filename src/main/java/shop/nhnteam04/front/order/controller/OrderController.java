package shop.nhnteam04.front.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.service.OrderService;
import shop.nhnteam04.front.order.service.PaymentService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // 유저 주문 내역
    @GetMapping("/my-list")
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
        // TODO: html 만들기
        ModelAndView mav = new ModelAndView("order-detail");

        OrderDetailResponse response = orderService.getOrder(orderId);
        mav.addObject("orderDetail", response);

        return mav;
    }

    // 주문서 작성
    @GetMapping("/order")
    public ModelAndView orderForm(@AuthenticationPrincipal SecurityUser user){
        ModelAndView mav = new ModelAndView("order-window");

        // TODO: 회원의 사용 가능 포인트 조회
        if(user != null) {
            int point = paymentService.getAvailablePoint(user.getId());
            mav.addObject("point", point);
            mav.addObject("userId", user.getId());
        }

        return mav;
    }

    @PostMapping("/order")
    public String order(HttpServletRequest req, RedirectAttributes re){
        String userId = req.getParameter("userId");
        String ordererName = req.getParameter("ordererName");

        CreateOrderRequest request = CreateOrderRequest.builder().build();

        /*

        OrderResponse orderResponse = orderService.createOrder(userId, );
        re.addAttribute("orderNumber", orderResponse.getOrderNumber());
        re.addAttribute("finalPrice", orderResponse.getFinalPrice());*/

        re.addAttribute("orderNumber", "dasweefeqq");
        re.addAttribute("finalPrice", 10000);

        return "redirect:/payment";
    }

    // 주문 취소
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") long orderId) {
        return "redirect:/order/my-list";
    }

    // 주문 반품
    @PostMapping("/return")
    public String returnOrder(@AuthenticationPrincipal SecurityUser user,
                              @RequestParam("orderId") long orderId) {
        return "redirect:/order/my-list";
    }

}
