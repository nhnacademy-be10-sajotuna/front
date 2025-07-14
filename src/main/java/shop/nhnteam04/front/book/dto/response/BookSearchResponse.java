package shop.nhnteam04.front.book.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookSearchResponse {
    private String isbn;
    private String title;
    private String author;
    private Double sellingPrice;
    private Double originalPrice;
    private Double averageRating;
    private String imageUrl;
    private Double popularity;
    private LocalDate publishedDate;
    private Integer reviewCount;
    private int searchCount;


}
