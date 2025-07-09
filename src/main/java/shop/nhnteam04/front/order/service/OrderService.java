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
import shop.nhnteam04.front.order.dto.orders.response.ReturnReason;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    public OrderInfoResponse getOrderInfo(String orderNumber) {
        if (orderNumber == null) {
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getOrderInfo(orderNumber);
    }

    // 회원의 주문내역 조회
    public List<OrderInfoResponse> getUserOrders(Long userId, Pageable pageable) {
        if (userId < 0) {
            throw new IllegalArgumentException();
        }
        Page<OrderInfoResponse> page = orderFeignClient.getUserOrders(userId, pageable);

        return page.getContent();
    }

    // 주문 조회
    public OrderDetailResponse getOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getOrder(orderId);
    }

    public OrderDetailResponse getGuestOrder(String orderNumber) {
        if (orderNumber == null || orderNumber.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getGuestOrder(orderNumber);
    }

    // 상품 주문 생성
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        return orderFeignClient.createOrder(userId, request);
    }

    // localdatetime 형태 변환
    public String convertTime(String dateTime) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, parser);
        ldt.withSecond(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return ldt.format(formatter);
    }

    // 주문 취소 처리
    public void cancelOrder(long userId, long orderId) {
        if (userId < 0 || orderId < 0) {
            throw new IllegalArgumentException();
        }
        orderFeignClient.cancelOrder(userId, orderId);
    }

    // 주문 반품
    public void returnOrder(long userId, long orderId, ReturnReason returnReason) {
        if (userId < 0 || orderId < 0 || returnReason == null) {
            throw new IllegalArgumentException();
        }
        orderFeignClient.returnOrder(userId, orderId, returnReason);
    }
}
