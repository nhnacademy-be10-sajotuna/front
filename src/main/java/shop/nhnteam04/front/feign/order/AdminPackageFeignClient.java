package shop.nhnteam04.front.feign.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.PackageResponse;

@FeignClient(name = "gateway/order-api", contextId = "adminPackageFeignClient")
public interface AdminPackageFeignClient {
    @PostMapping("/api/admin/packages/package")
    PackageResponse createPackage(@RequestBody PackageRequest packageRequest);

    @PutMapping("/api/admin/packages/package/{package-id}")
    void updatePackage(@PathVariable("package-id") Long packageId, @RequestBody PackageRequest packageRequest);

    @DeleteMapping("/api/admin/packages/package/{package-id}")
    void deletePackage(@PathVariable("package-id") Long packageId);
}
