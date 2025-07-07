package shop.nhnteam04.front.order.dto.orders.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String orderNumber;
    
    // Orderer 정보
    private Long userId;
    private String ordererName;
    private String ordererPhoneNumber;
    private String ordererEmail;
    
    private boolean isUserOrder;
    
    // ShippingInfo 정보
    private String recipientName;
    private String recipientPhoneNumber;
    private String recipientEmail;
    private String recipientAddress;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime shippingStartDate;
    private LocalDateTime shippingEndDate;
    
    // OrderPrice 정보
    private int totalProductPrice;
    private int packagingPrice;
    private int deliveryPrice;
    private int totalPrice;
    
    // Discounts 정보
    private int couponDiscountAmount;
    private int usedPoint;
    private int totalDiscountAmount;
    
    // 최종 금액
    private int finalPrice;
    
    private OrderStatus status;
    private LocalDateTime createdAt;
}
