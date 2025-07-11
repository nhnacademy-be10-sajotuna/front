package shop.nhnteam04.front.cart.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CartItemResponse {
    private Long cartItemId;
    private String isbn;
    private Long quantity;

    private String title;
    private String imageUrl;
    private Double originalPrice;
    private Double sellingPrice;
    private Boolean giftWrappingAvailable;
    private List<Long> categoryIds;
}
