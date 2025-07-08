package shop.nhnteam04.front.review.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewStatsResponse {
    private String isbn;
    private Double averageRating;
    private Long reviewCount;
}

