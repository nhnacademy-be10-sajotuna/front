package shop.nhnteam04.front.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.service.BookService;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.*;
import shop.nhnteam04.front.order.dto.orders.request.ReturnReason;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;
    private final BookService bookService;

    public OrderInfoResponse getOrderInfo(String orderNumber) {
        if (orderNumber == null) {
            throw new IllegalArgumentException();
        }
        return orderFeignClient.getOrderInfo(orderNumber);
    }

    // 회원의 주문내역 조회
    public Page<OrderInfoResponse> getUserOrders(Long userId, Pageable pageable) {
        if (userId < 0) {
            throw new IllegalArgumentException();
        }

        return orderFeignClient.getUserOrders(userId, pageable);
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

    public OrderFormResponse getOrderForm(Long userId) {
        return orderFeignClient.getOrderForm(userId);
    }

    public List<BookProductResponse> getBookProducts(List<OrderProductResponse> orderProductResponseList) {
        List<BookProductResponse> bookProductResponseList = new ArrayList<>();

        orderProductResponseList.forEach(orderProductResponse -> {
            BookResponse bookResponse = bookService.getBookByIsbn(orderProductResponse.getIsbn());
            bookProductResponseList.add(new BookProductResponse(bookResponse, orderProductResponse));
        });

        return bookProductResponseList;
    }
}
