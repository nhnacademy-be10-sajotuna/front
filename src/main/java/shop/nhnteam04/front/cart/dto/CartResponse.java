package shop.nhnteam04.front.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private String cartId;
    private List<CartItemResponse> items;
}
