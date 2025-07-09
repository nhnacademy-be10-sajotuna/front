package shop.nhnteam04.front.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.service.OrderService;
import shop.nhnteam04.front.order.service.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // 주문서 작성
    @GetMapping
    public ModelAndView orderForm(@AuthenticationPrincipal SecurityUser user){
        ModelAndView mav = new ModelAndView("order/order-window");

        // TODO: 장바구니에 있는 상품들 가져오기

        // TODO: 회원의 사용 가능 포인트 조회
        if(user != null) {
            int point = paymentService.getAvailablePoint(user.getId());
            mav.addObject("point", point);
            mav.addObject("userId", user.getId());
        }

        return mav;
    }

    @PostMapping
    public String order(HttpServletRequest req, RedirectAttributes re){
        String userId = req.getParameter("userId");
        String ordererName = req.getParameter("ordererName");
        String expectedDeliveryDate = orderService.convertTime(req.getParameter("expectedDeliveryDate"));

        /*
        CreateOrderRequest request = CreateOrderRequest.builder()
                .ordererName(ordererName)
                .build();

        OrderResponse orderResponse = orderService.createOrder(userId, request);

        re.addAttribute("orderNumber", orderResponse.getOrderNumber());
        re.addAttribute("finalPrice", orderResponse.getFinalPrice());
        */

        re.addAttribute("orderNumber", "wdawdrfsedfcdsw");

        return "redirect:/payment";
    }

    @GetMapping("/detail/{order-id}")
    public ModelAndView OrderDetail(@PathVariable("order-id") Long orderId) {
        ModelAndView mav = new ModelAndView("order/order-detail");

        OrderDetailResponse response = orderService.getOrder(orderId);
        mav.addObject("orderDetail", response);

        return mav;
    }

    // 주문 취소
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") long orderId) {
        paymentService.cancelPayment(orderId, "cancel");

        return "redirect:/order/my-list";
    }

    // 주문 반품
    @PostMapping("/return")
    public String returnOrder(@AuthenticationPrincipal SecurityUser user,
                              @RequestParam("orderId") long orderId) {
        paymentService.cancelPayment(orderId, "return");

        return "redirect:/order/my-list";
    }
}
