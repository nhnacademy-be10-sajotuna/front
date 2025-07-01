package shop.nhnteam04.front.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartSummaryResponse {
    private int productTotal;   // 상품금액 (originalPrice * quantity)
    private int discountTotal;  // 할인금액 ((originalPrice - sellingPrice) * quantity)
    private int payAmount;      // 결제예정금액 (sellingPrice * quantity)

    public static CartSummaryResponse of(CartResponse cart) {
        int productTotal = 0;
        int discountTotal = 0;
        int payAmount = 0;

        for (CartItemResponse item : cart.getItems()) {
            int quantity = item.getQuantity().intValue();
            int original = item.getOriginalPrice().intValue();
            int selling = item.getSellingPrice().intValue();

            productTotal += original * quantity;
            discountTotal += (original - selling) * quantity;
            payAmount += selling * quantity;
        }

        return new CartSummaryResponse(productTotal, discountTotal, payAmount);
    }
}
