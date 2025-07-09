package shop.nhnteam04.front.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.feign.point.PointFeignClient;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderFeignClient orderFeignClient;
    private final PointFeignClient pointFeignClient;

    // 결제 승인 요청
    public PaymentResponse confirmPayment(PaymentConfirmRequest request) {
        if(request == null){
            throw new IllegalArgumentException("Request cannot be null");
        }
        return orderFeignClient.confirmPayment(request);
    }

    // 사용 가능한 포인트 가져옴
    public Integer getAvailablePoint(Long userId) {
        if(userId < 0){
            throw new IllegalArgumentException();
        }
        return pointFeignClient.getAvailablePoint(userId);
    }
}
