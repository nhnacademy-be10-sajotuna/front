package shop.nhnteam04.front.cart.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long cartItemId;
    private String bookId;
    private Long quantity;
    // book api 추가로 받아옴(장바구니 페이지에 필요)
    private String title;
    private String imageUrl;
    private Double originalPrice;
    private Double sellingPrice;
}
