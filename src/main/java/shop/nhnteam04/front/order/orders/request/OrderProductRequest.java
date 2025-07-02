package shop.nhnteam04.front.order.orders.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {
    private long orderPackagingId;

    @NotBlank
    private String isbn;

    @NotNull
    @PositiveOrZero
    private int qty;

    @NotNull
    @PositiveOrZero
    private int amount;

    //TODO: 상품 쿠폰 추가

    @NotNull
    private Boolean packagingRequest;
}
