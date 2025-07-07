package shop.nhnteam04.front.order.dto.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.nhnteam04.front.order.dto.point.type.Grade;

@AllArgsConstructor
@Getter
public class GradePointPolicyResponse {

    private Grade grade;
    private int minTotalOrderPrice;
    private int maxTotalOrderPrice;
    private int pointRate;
}
