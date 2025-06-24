package shop.nhnteam04.front.cart.dto;

import lombok.Data;

@Data
public class UserCartItemResponse implements CartItemResponse {
    private Long cartItemId;
    private Long bookId;
    private Long quantity;
}
