package shop.nhnteam04.front.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    // 결제 승인 요청
    public PaymentResponse confirmPayment(PaymentConfirmRequest request) {
        return orderFeignClient.confirmPayment(request);
    }

    // 사용 가능한 포인트 가져옴
    public Integer getAvailablePoint(Long userId) {
        return orderFeignClient.getAvailablePoint(userId);
    }

    // 상품 주문 생성
    public OrderResponse createOrder(Long userId, CreateOrderRequest request){
        return orderFeignClient.createOrder(userId, request);
    }
}
