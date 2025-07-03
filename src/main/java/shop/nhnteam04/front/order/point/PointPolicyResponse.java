package shop.nhnteam04.front.order.point;

import lombok.Builder;
import lombok.Getter;
import shop.nhnteam04.front.order.point.domain.CalculationMode;
import shop.nhnteam04.front.order.point.domain.PointPolicyType;

@Builder
@Getter
public class PointPolicyResponse {
    private Long id;

    private PointPolicyType type;

    private int value;

    private CalculationMode calculationMode;
}
