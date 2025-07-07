package shop.nhnteam04.front.order.dto.orders.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderInfoResponse {
    private Long orderId;
    private String orderNumber;
    private int finalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String firstProductIsbn;
    private int totalProductCount;

}
