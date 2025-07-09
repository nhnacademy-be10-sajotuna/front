package shop.nhnteam04.front.order.dto.orders.response;

import lombok.Getter;

@Getter
public enum ReturnReason {

    UNUSED(10, true),
    DAMAGED(30, false),
    DEFECTIVE(30, false);

    private final int maxDays;
    private final boolean deductShippingFee;

    ReturnReason(int maxDays, boolean deductShippingFee) {
        this.maxDays = maxDays;
        this.deductShippingFee = deductShippingFee;
    }
}
