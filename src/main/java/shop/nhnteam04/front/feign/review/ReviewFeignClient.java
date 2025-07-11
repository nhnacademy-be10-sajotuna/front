package shop.nhnteam04.front.feign.review;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.review.request.ReviewCreateRequest;
import shop.nhnteam04.front.review.request.ReviewUpdateRequest;
import shop.nhnteam04.front.review.response.ReviewResponse;
import java.util.List;

@FeignClient(name = "gateway/review-api")
public interface ReviewFeignClient {
    @GetMapping("/api/reviews/books/isbn/{isbn}")
    List<ReviewResponse> getReviewsByBook(@RequestParam("isbn") String isbn);
    
    @GetMapping("/api/reviews/books/user-id/{user-id}")
    List<ReviewResponse> getReviewsByUser(@RequestParam("user-id") Long userId);

    @PostMapping(value = "/api/reviews", consumes = "multipart/form-data")
    ReviewResponse createReview(
            @RequestPart("request") ReviewCreateRequest request,
            @RequestHeader("X-User-Id") Long userId
    );

    @PutMapping(value = "/api/reviews/{id}", consumes = "multipart/form-data")
    ReviewResponse updateReview(
            @PathVariable("id") Long id,
            @RequestPart("request") ReviewUpdateRequest request,
            @RequestHeader("X-User-Id") Long userId
    );

    @GetMapping("/api/reviews/{id}")
    ReviewResponse getReviewById(@PathVariable("id") Long id);
}
