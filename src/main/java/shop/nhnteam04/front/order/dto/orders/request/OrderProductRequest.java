package shop.nhnteam04.front.order.dto.orders.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    private Long bookCouponId;

    @NotNull
    private Boolean packagingRequest;

    // 카테고리 ID 목록 (선택적)
    private Set<Long> categoryIds;
}
