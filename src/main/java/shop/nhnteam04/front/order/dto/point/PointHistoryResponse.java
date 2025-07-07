package shop.nhnteam04.front.order.dto.point;

import lombok.Builder;
import lombok.Getter;
import shop.nhnteam04.front.order.dto.point.type.PointHistoryType;

import java.time.LocalDateTime;

@Builder
@Getter
public class PointHistoryResponse {
    private Long id;

    private Long userId;

    private int amount;

    private PointHistoryType type;

    private String description;

    private LocalDateTime createdAt;
}
