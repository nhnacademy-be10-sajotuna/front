package shop.nhnteam04.front.cart.dto;

import java.util.List;

public interface CartResponse {
    List<? extends CartItemResponse> getItems();
    String Id();
}
