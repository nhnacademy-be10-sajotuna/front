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

        mav.addObject("orderNumber", orderNumber);
        mav.addObject("amount", response.getFinalPrice());

        return mav;
    }

    // 일반 결제
    @PostMapping
    public String payment(HttpServletRequest req, RedirectAttributes re){
        String orderNumber = req.getParameter("orderNumber");
        String amount = req.getParameter("amount");

        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.CARD)
                .orderNumber(orderNumber)
                .amount(Integer.parseInt(amount)).build();
        PaymentResponse response = paymentService.confirmPayment(request);

        re.addAttribute("orderNumber", orderNumber);
        re.addAttribute("amount", response.getAmount());

        return "redirect:/payment/success";
    }

    // 일반 결제 완료
    @GetMapping("/success")
    public ModelAndView successPage(@RequestParam("orderNumber") String orderNumber,
                                    @RequestParam("amount") int amount) {
        ModelAndView mav = new ModelAndView("payment/payment-success");

        mav.addObject("orderNumber", orderNumber);
        mav.addObject("amount", amount);

        return mav;
    }

    // 토스 결제 완료
    @GetMapping("/toss/success")
    public ModelAndView paymentSuccess(@RequestParam("orderNumber") String orderNumber,
                                       @RequestParam("amount") int amount,
                                       @RequestParam("paymentKey") String paymentKey) {
        ModelAndView mav = new ModelAndView("payment/payment-success");

        PaymentConfirmRequest request = PaymentConfirmRequest.builder()
                .paymentMethod(PaymentMethod.TOSS)
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .amount(amount).build();
        PaymentResponse response = paymentService.confirmPayment(request);

        mav.addObject("orderNumber", orderNumber);
        mav.addObject("amount", response.getAmount());

        return mav;
    }

    // 결제 실패
    @GetMapping("/fail")
    public String paymentFail() {
        return "payment/payment-fail";
    }
}
