package shop.nhnteam04.front.review.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String maskedEmail;
    private String isbn;
    private int rating;
    private String content;
    private LocalDateTime postedAt;
    private String filePath;
}
