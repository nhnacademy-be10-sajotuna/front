package shop.nhnteam04.front.order.payment;

import lombok.*;

@Builder
@Getter
public class PaymentConfirmRequest {
    private PaymentMethod paymentMethod;

    private String orderNumber;

    private String paymentKey;

    private int amount;
}
