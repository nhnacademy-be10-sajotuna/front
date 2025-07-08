package shop.nhnteam04.front.order.dto.orders.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private String ordererName;

    private String ordererPhoneNumber;

    private String ordererEmail;

    private String recipientName;

    private String recipientPhoneNumber;

    private String recipientEmail;

    private String recipientAddress;

    @Future
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime expectedDeliveryDate;

    private Long orderCouponId;

    @Min(0)
    private Integer usedPoint;

    @NotEmpty
    @Valid
    private List<OrderProductRequest> items;
}

