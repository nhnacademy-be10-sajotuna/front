package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderInfoResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.dto.orders.response.PackageResponse;
import shop.nhnteam04.front.order.dto.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.dto.payment.PaymentResponse;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;

import java.util.List;

@FeignClient(name= "order-api")
public interface OrderFeignClient {

    // orders
    @GetMapping("/api/orders/info/{order-number}")
    OrderInfoResponse getOrderInfo(@PathVariable("order-number") String orderNumber);

    @GetMapping("/api/orders/{order-id}")
    OrderDetailResponse getOrder(@PathVariable("order-id") Long orderId);

    @GetMapping("/api/orders/guest/{order-number}")
    OrderDetailResponse getGuestOrder(@PathVariable("order-number") String orderNumber);

    @GetMapping("/api/orders/user")
    Page<OrderInfoResponse> getUserOrders(@RequestHeader("X-User-Id") Long userId, Pageable pageable);

    @PostMapping("/api/orders")
    OrderResponse createOrder(@RequestHeader(value = "X-User-Id", required = false) Long userId, @RequestBody CreateOrderRequest request);

    @PutMapping("/api/orders/returned/{order-id}")
    void returnedOrder(@PathVariable("order-id") Long orderId, @RequestHeader("X-User-Id") Long userId);

    // orderPackage
    @GetMapping("api/orders/package")
    List<PackageResponse> getPackages();

    @PostMapping("/api/admin/packages/package")
    PackageResponse createPackage(@RequestBody PackageRequest packageRequest);

    @PutMapping("/api/admin/packages/package/{package-id}")
    void updatePackage(@PathVariable("package-id") Long packageId, @RequestBody PackageRequest request);


    // payment
    @PostMapping("/api/payments/confirm")
    PaymentResponse confirmPayment(@RequestBody PaymentConfirmRequest request);



    // point
    @GetMapping("/api/points")
    List<PointHistoryResponse> getPointsByUserId(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/points/available")
    Integer getAvailablePoint(@RequestHeader("X-User-Id") Long userId);



}
