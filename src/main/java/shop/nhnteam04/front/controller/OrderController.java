package shop.nhnteam04.front.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.nhnteam04.front.order.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.payment.PaymentMethod;
import shop.nhnteam04.front.order.payment.PaymentResponse;
import shop.nhnteam04.front.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 결제 창
    @GetMapping("/payment")
    public String registerForm() {
        return "payment";
    }

    // 토스 결제 완료
    @GetMapping("/payment/success")
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

        //PaymentResponse response = orderService.confirmPayment(request);

        return "payment-success";
    }

    @GetMapping("/payment/fail")
    public String paymentFail() {
        return "payment-fail";
    }
}
