package shop.nhnteam04.front.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    public OrderInfoResponse getOrderInfo(String orderNumber) {
        if(orderNumber == null) {
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getOrderInfo(orderNumber);
    }

    // 회원의 주문내역 조회
    public List<OrderInfoResponse> getUserOrders(Long userId, Pageable pageable){
        if(userId < 0){
            throw new IllegalArgumentException();
        }
        Page<OrderInfoResponse> page = orderFeignClient.getUserOrders(userId, pageable);

        return page.getContent();
    }

    // 주문 조회
    public OrderDetailResponse getOrder(Long orderId){
        if(orderId == null){
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getOrder(orderId);
    }

    // 상품 주문 생성
    public OrderResponse createOrder(Long userId, CreateOrderRequest request){
        if(request == null){
            throw new IllegalArgumentException("Request cannot be null");
        }
        return orderFeignClient.createOrder(userId, request);
    }

    // localdatetime 형태 변환
    public String convertTime(String dateTime){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, parser);
        ldt.withSecond(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return ldt.format(formatter);
    }
}
