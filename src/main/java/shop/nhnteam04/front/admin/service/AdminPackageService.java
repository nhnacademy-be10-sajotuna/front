package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.AdminPackageFeignClient;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.PackageResponse;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminPackageService {
    private final AdminPackageFeignClient adminPackageFeignClient;
    private final OrderFeignClient orderFeignClient;

    public List<PackageResponse> getPackageList(){
        return orderFeignClient.getPackages();
    }

    public void updatePackage(long packageId, PackageRequest packageRequest) {
        if(packageId <= 0 || packageRequest == null){
            throw new IllegalArgumentException();
        }
        adminPackageFeignClient.updatePackage(packageId, packageRequest);
    }

    public void deletePackage(long packageId) {
        if(packageId <= 0){
            throw new IllegalArgumentException();
        }
        adminPackageFeignClient.deletePackage(packageId);
    }

    public void createPackage(PackageRequest packageRequest) {
        if(packageRequest == null){
            throw new IllegalArgumentException();
        }
        adminPackageFeignClient.createPackage(packageRequest);
    }
}
