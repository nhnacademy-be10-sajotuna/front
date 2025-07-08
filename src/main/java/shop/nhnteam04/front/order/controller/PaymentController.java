package shop.nhnteam04.front.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentMethod;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;
import shop.nhnteam04.front.order.service.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 창
    @GetMapping
    public ModelAndView paymentPage(@RequestParam String orderNumber, @RequestParam String finalPrice) {
        ModelAndView mav = new ModelAndView("payment-window");

        mav.addObject("orderId", orderNumber);
        mav.addObject("amount", finalPrice);

        return mav;
    }

    // 토스 결제 성공
    @GetMapping("/success")
    public ModelAndView paymentSuccess(@RequestParam("orderId") String orderId,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount
    ) {
        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.TOSS)
                .orderNumber(orderId)
                .paymentKey(paymentKey)
                .amount(amount).build();

        ModelAndView mav = new ModelAndView("payment-success");

        PaymentResponse response = paymentService.confirmPayment(request);
        mav.addObject("response", response);

        return mav;
    }

    // 결제 실패
    @GetMapping("/fail")
    public String paymentFail() {
        return "payment-fail";
    }
}
