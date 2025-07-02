package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.orders.request.PackageRequest;
import shop.nhnteam04.front.order.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.orders.response.OrderResponse;
import shop.nhnteam04.front.order.orders.response.PackageResponse;
import shop.nhnteam04.front.order.payment.PaymentConfirmRequest;
import shop.nhnteam04.front.order.payment.PaymentResponse;

import java.util.List;

@FeignClient(name= "order-api")
public interface OrderFeignClient {

    @GetMapping("/api/orders/{order-id}")
    OrderDetailResponse getOrder(@PathVariable("order-id") String orderId);

    @GetMapping("/api/orders/guest/{order-number}")
    OrderDetailResponse getGuestOrder(@PathVariable("order-number") String orderNumber);

    @PostMapping("/api/orders/user")
    OrderResponse createUserOrder(@RequestHeader("X-User-Id") Long userId, @RequestBody CreateOrderRequest request);

    @PostMapping("/api/orders/guest")
    OrderResponse createGuestOrder(@RequestBody CreateOrderRequest request);

    @PutMapping("/api/orders/returned/{order-id}")
    void returnedOrder(@PathVariable("order-id") Long orderId, @RequestHeader("X-User-Id") Long userId);

    @GetMapping("api/orders/package")
    List<PackageResponse> getPackages();

    @PostMapping("/api/admin/packages/package")
    PackageResponse createPackage(@RequestBody PackageRequest packageRequest);

    @PutMapping("/api/admin/packages/package/{package-id}")
    void updatePackage(@PathVariable("package-id") Long packageId, @RequestBody PackageRequest request);


    @PostMapping("/api/payments/confirm")
    PaymentResponse confirmPayment(@RequestBody PaymentConfirmRequest request);
}
