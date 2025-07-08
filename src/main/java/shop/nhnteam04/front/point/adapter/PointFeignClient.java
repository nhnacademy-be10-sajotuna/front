package shop.nhnteam04.front.point.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;

@FeignClient(name = "order-api" ,contextId = "pointFeignClient")
public interface PointFeignClient {

    @GetMapping("/api/points")
    Page<PointHistoryResponse> getPointHistory(@RequestHeader("X-User-Id") Long userId, Pageable pageable);
}
