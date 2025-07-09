package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.*;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;

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
    void returnOrder(@PathVariable("order-id") Long orderId, @RequestHeader("X-User-Id") Long userId,
                     @RequestParam("return-reason") ReturnReason returnReason);

    @PutMapping("/api/orders/{order-id}/cancel")
    void cancelOrder(@PathVariable("order-id") Long orderId);



    // orderPackage
    @GetMapping("api/orders/package")
    List<PackageResponse> getPackages();

    @PostMapping("/api/admin/packages/package")
    PackageResponse createPackage(@RequestBody PackageRequest packageRequest);

    @PutMapping("/api/admin/packages/package/{package-id}")
    void updatePackage(@PathVariable("package-id") Long packageId, @RequestBody PackageRequest request);

    @DeleteMapping("/api/admin/packages/package/{package-id}")
    void deletePackage(@PathVariable("package-id") Long packageId);



    // payment
    @PostMapping("/api/payments/confirm")
    PaymentResponse confirmPayment(@RequestBody PaymentConfirmRequest request);

    @PutMapping("/api/payments/cancel/{order-id}")
    void cancelPayment(@PathVariable("order-id") Long orderId, @RequestParam String cancelReason);



    // point
    @GetMapping("/api/points")
    List<PointHistoryResponse> getPointsByUserId(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/points/available")
    Integer getAvailablePoint(@RequestHeader("X-User-Id") Long userId);


    // review
    @GetMapping("/api/orders/product/review-eligible/{userId}/{isbn}")
    boolean isEligibleForReview(@PathVariable Long userId, @PathVariable String isbn);
}