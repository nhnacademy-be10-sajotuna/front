package shop.nhnteam04.front.order.dto.payment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentResponse {
    private Long id;
    private PaymentMethod method;
    private Integer amount;
    private LocalDateTime createdAt;
}
