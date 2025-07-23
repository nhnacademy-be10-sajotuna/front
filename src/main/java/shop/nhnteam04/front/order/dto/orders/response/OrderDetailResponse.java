package shop.nhnteam04.front.order.dto.orders.response;

import lombok.*;
import shop.nhnteam04.front.order.dto.payment.PaymentMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDetailResponse {
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
    private LocalDate expectedDeliveryDate;
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
    private LocalDateTime orderCreatedAt;
    
    // 주문 상품 목록
    private List<OrderProductResponse> items;
    
    // Payment 정보
    private PaymentMethod paymentMethod;
    private Integer paymentAmount;
    private LocalDateTime paymentCreatedAt;
}
