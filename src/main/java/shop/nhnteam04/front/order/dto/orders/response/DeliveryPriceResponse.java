package shop.nhnteam04.front.order.dto.orders.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryPriceResponse {
    private Integer freeDeliveryMinPrice;

    private Integer deliveryPrice;
}