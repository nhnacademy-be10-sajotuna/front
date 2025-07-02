package shop.nhnteam04.front.order.orders.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProductResponse {
    private Long id;
    private String isbn;
    private PackageResponse packageResponse;
    private int qty;
    private int amount;
    private Boolean packagingRequest;
}
