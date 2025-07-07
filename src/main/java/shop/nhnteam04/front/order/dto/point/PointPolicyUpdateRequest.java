package shop.nhnteam04.front.order.dto.point;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointPolicyUpdateRequest {

    @Min(1)
    private int value;
}
