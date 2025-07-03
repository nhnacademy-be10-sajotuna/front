package shop.nhnteam04.front.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.order.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.orders.response.OrderResponse;
import shop.nhnteam04.front.order.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.payment.PaymentMethod;
import shop.nhnteam04.front.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 결제 창
    @GetMapping("/order/payment")
    public ModelAndView userOrderForm(@RequestHeader(value = "X-User-Id", required = false) Long userId) {
        ModelAndView mav = new ModelAndView("payment");

        // TODO: 회원의 사용 가능 포인트 조회
        /*
        if(userId != null) {
            //int point = orderService.getAvailablePoint(userId);
            //mav.addObject("point", point);
        }
        */

        mav.addObject("userId", userId);

        return mav;
    }

    // 주문 생성
    @PostMapping("/order/payment")
    public OrderResponse createOrder(@RequestHeader(value = "X-User-Id", required = false) Long userId, @RequestBody CreateOrderRequest request){
        return orderService.createOrder(userId, request);
    }

    // 토스 결제 완료
    @GetMapping("/order/payment/success")
    public String paymentSuccess(@RequestParam("orderId") String orderId,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount
    ) {
        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.TOSS)
                .orderNumber(orderId)
                .paymentKey(paymentKey)
                .amount(amount).build();

        log.info("PaymentConfirmRequest: {}", request);

        // TODO: 결제 승인
        //PaymentResponse response = orderService.confirmPayment(request);

        return "payment-success";
    }

    // 결제 실패
    @GetMapping("/order/payment/fail")
    public String paymentFail() {
        return "payment-fail";
    }
}
