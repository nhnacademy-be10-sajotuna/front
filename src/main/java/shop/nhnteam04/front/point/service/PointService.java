package shop.nhnteam04.front.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.order.dto.point.PointHistoryResponse;
import shop.nhnteam04.front.point.adapter.PointFeignClient;


@Service
@RequiredArgsConstructor
public class PointService {

    private final PointFeignClient pointFeignClient;

    public Page<PointHistoryResponse> getPointHistory(Long userId, Pageable pageable) {
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return pointFeignClient.getPointHistory(userId, pageable);
    }
}
