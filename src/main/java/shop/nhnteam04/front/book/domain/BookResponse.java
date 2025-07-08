package shop.nhnteam04.front.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class BookResponse {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicationDate;
    private Integer pageCount;
    private String imageUrl;
    private String description;
    private Double originalPrice;
    private Double sellingPrice;
    private Double discountRate;
    private Boolean giftWrappingAvailable;
    private Integer likes;
    private List<List<CategoryResponse>> categories;
    private Set<String> tags; // 책에 연결된 태그
    private Double averageRating;
    private int reviewCount;
    private int viewCount;
}