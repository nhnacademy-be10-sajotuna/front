package shop.nhnteam04.front.cart.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private String bookId;
    private Long quantity;
}
