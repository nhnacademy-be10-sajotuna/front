package shop.nhnteam04.front.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserGradePolicyDto {
    private int minAmount;
    private int maxAmount;
    private BigDecimal pointEarningRate;
}
