package shop.nhnteam04.front.order.dto.point;

import lombok.Builder;
import lombok.Getter;
import shop.nhnteam04.front.order.dto.point.type.CalculationMode;
import shop.nhnteam04.front.order.dto.point.type.PointPolicyType;

@Builder
@Getter
public class PointPolicyResponse {
    private Long id;

    private PointPolicyType type;

    private int value;

    private CalculationMode calculationMode;
}
