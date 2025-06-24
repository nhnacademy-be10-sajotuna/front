package shop.nhnteam04.front.cart.dto;

import lombok.Data;

@Data
public class GuestCartItemResponse implements CartItemResponse {
    private Long bookId;
    private Long quantity;
}
