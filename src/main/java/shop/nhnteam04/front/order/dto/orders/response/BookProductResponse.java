package shop.nhnteam04.front.order.dto.orders.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import shop.nhnteam04.front.book.dto.response.BookResponse;

@Data
@NoArgsConstructor
public class BookProductResponse {
    private String isbn;
    private String title;
    private String imageUrl;
    private int qty;
    private int amount;

    public BookProductResponse(BookResponse bookResponse, OrderProductResponse orderProductResponse){
        this.isbn = orderProductResponse.getIsbn();
        this.title = bookResponse.getTitle();
        this.imageUrl = bookResponse.getImageUrl();
        this.qty = orderProductResponse.getQty();
        this.amount = orderProductResponse.getAmount();
    }
}
