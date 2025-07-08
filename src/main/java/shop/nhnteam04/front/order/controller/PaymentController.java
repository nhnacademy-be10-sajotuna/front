package shop.nhnteam04.front.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentMethod;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;
import shop.nhnteam04.front.order.service.OrderService;
import shop.nhnteam04.front.order.service.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    // 결제 창
    @GetMapping
    public ModelAndView paymentPage(@RequestParam String orderNumber) {
        ModelAndView mav = new ModelAndView("payment/payment-window");

        OrderInfoResponse response = orderService.getOrderInfo(orderNumber);

        mav.addObject("orderId", orderNumber);
        mav.addObject("amount", response.getFinalPrice());

        return mav;
    }

    @PostMapping
    public String payment(HttpServletRequest req, RedirectAttributes re){
        String orderId = req.getParameter("orderId");
        String amount = req.getParameter("amount");

        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.CARD)
                .orderNumber(orderId)
                .amount(Integer.parseInt(amount)).build();
        PaymentResponse response = paymentService.confirmPayment(request);

        re.addAttribute("orderId", orderId);
        re.addAttribute("amount", response.getAmount());

        return "redirect:/payment/success";
    }

    // 결제 완료
    @GetMapping("/success")
    public ModelAndView successPage(@RequestParam("orderId") String orderId,
                                    @RequestParam("amount") int amount) {
        ModelAndView mav = new ModelAndView("payment/payment-success");

        mav.addObject("orderId", orderId);
        mav.addObject("amount", amount);

        return mav;
    }

    // 토스 결제 성공
    @GetMapping("/toss/success")
    public ModelAndView paymentSuccess(@RequestParam("orderId") String orderId,
                                       @RequestParam("amount") int amount,
                                       @RequestParam("paymentKey") String paymentKey
    ) {
        ModelAndView mav = new ModelAndView("payment/toss-payment-success");

        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.TOSS)
                .orderNumber(orderId)
                .paymentKey(paymentKey)
                .amount(amount).build();
        PaymentResponse response = paymentService.confirmPayment(request);

        mav.addObject("orderId", orderId);
        mav.addObject("amount", response.getAmount());

        return mav;
    }

    // 결제 실패
    @GetMapping("/fail")
    public String paymentFail() {
        return "payment/payment-fail";
    }
}
