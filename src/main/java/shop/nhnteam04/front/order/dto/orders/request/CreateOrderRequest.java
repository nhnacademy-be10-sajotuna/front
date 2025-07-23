package shop.nhnteam04.front.order.dto.orders.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotBlank
    private String ordererName;

    @NotBlank
    private String ordererPhoneNumber;

    @NotBlank
    @Email
    private String ordererEmail;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String recipientPhoneNumber;

    @Email
    private String recipientEmail;

    @NotBlank
    private String recipientAddress;

    @Future
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate expectedDeliveryDate;

    private Long orderCouponId;

    @Min(0)
    private Integer usedPoint;

    @NotEmpty
    @Valid
    private List<OrderProductRequest> items;
}

