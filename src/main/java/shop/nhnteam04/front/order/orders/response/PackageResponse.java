package shop.nhnteam04.front.order.orders.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackageResponse {
    private Long id;
    private String packaging;
    private Integer price;
}
