package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.dto.orders.response.PackageResponse;

@FeignClient(name = "gateway/order-api", contextId = "adminOrderFeignClient")
public interface AdminOrderFeignClient {

    @GetMapping("/api/admin/orders")
    Page<OrderResponse> getAllOrders(Pageable pageable);

    @GetMapping("/api/admin/orders/{status}")
    Page<OrderResponse> getStatusOrders(@PathVariable String status, Pageable pageable);

    @PostMapping("/api/admin/packages/package")
    PackageResponse createPackage(@RequestBody PackageRequest packageRequest);

    @PutMapping("/api/admin/packages/package/{package-id}")
    void updatePackage(@PathVariable("package-id") Long packageId, @RequestBody PackageRequest request);

    @PutMapping("/api/admin/orders/{order-id}/delivery")
    void shippedOrder(@PathVariable("order-id") Long orderId);
}
