package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.*;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;

import java.util.List;

@FeignClient(name = "gateway/order-api")
public interface OrderFeignClient {

    // orders
    @GetMapping("/api/orders/info/{order-number}")
    OrderInfoResponse getOrderInfo(@PathVariable("order-number") String orderNumber);

    @GetMapping("/api/orders/detail/{order-id}")
    OrderDetailResponse getOrder(@PathVariable("order-id") Long orderId);

    @GetMapping("/api/orders/detail/guest/{order-number}")
    OrderDetailResponse getGuestOrder(@PathVariable("order-number") String orderNumber);

    @GetMapping("/api/orders/user")
    Page<OrderInfoResponse> getUserOrders(@RequestHeader("X-User-Id") Long userId, Pageable pageable);

    @PostMapping("/api/orders")
    OrderResponse createOrder(@RequestHeader(value = "X-User-Id", required = false) Long userId, @RequestBody CreateOrderRequest request);

    @PutMapping("/api/orders/{order-id}/return")
    void returnOrder(@RequestHeader("X-User-Id") Long userId, @PathVariable("order-id") Long orderId, @RequestParam("return-reason") ReturnReason returnReason);

    @PutMapping("/api/orders/{order-id}/cancel")
    void cancelOrder(@RequestHeader("X-User-Id") Long userId, @PathVariable("order-id") Long orderId);


    // orderPackage
    @GetMapping("api/orders/package")
    List<PackageResponse> getPackages();

    @GetMapping("api/orders/package/{package-id}")
    PackageResponse getPackages(@PathVariable("package-id") Long packageId);


    // payment
    @PostMapping("/api/payments/confirm")
    PaymentResponse confirmPayment(@RequestBody PaymentConfirmRequest request);


    // review
    @GetMapping("/api/orders/product/review-eligible/{userId}/{isbn}")
    boolean isEligibleForReview(@PathVariable Long userId, @PathVariable String isbn);
}