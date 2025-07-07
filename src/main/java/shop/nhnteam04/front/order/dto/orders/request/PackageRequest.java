package shop.nhnteam04.front.order.dto.orders.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageRequest {
    private String packaging;
    private Integer price;
}
