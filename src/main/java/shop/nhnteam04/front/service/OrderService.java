package shop.nhnteam04.front.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.order.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.payment.PaymentResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    // 결제 승인 요청
    public PaymentResponse confirmPayment(PaymentConfirmRequest request) {
        return orderFeignClient.confirmPayment(request);
    }
}
