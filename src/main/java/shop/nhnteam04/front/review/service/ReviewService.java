package shop.nhnteam04.front.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.feign.review.ReviewFeignClient;
import shop.nhnteam04.front.review.request.ReviewCreateRequest;
import shop.nhnteam04.front.review.request.ReviewUpdateRequest;
import shop.nhnteam04.front.review.response.ReviewResponse;
import shop.nhnteam04.front.utils.MaskingUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewFeignClient reviewFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final MinioService minioService;

    public List<ReviewResponse> getReviews(String isbn) {
        return reviewFeignClient.getReviews(isbn);
    }

    public ReviewResponse getReviewById(Long id) {
        return Optional.ofNullable(reviewFeignClient.getReviewById(id))
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
    }

    public void createReview(ReviewCreateRequest request, MultipartFile file, SecurityUser user) throws IOException {
         if (!orderFeignClient.isEligibleForReview(user.getId(), request.getIsbn())) {
             throw new IllegalStateException("리뷰를 작성할 자격이 없습니다.");
         }

        if (file != null && !file.isEmpty()) {
            String filePath = minioService.handleImageUpload(file);
            request.setFilePath(filePath);
        }

        request.setMaskedEmail(MaskingUtils.maskEmail(user.getEmail()));

        reviewFeignClient.createReview(request, user.getId());
    }

    public void updateReview(Long id, ReviewUpdateRequest request, MultipartFile file, Long userId) throws Exception {
        ReviewResponse existingReview = getReviewById(id); // 기존 리뷰 정보 조회 (getReviewById로 대체하여 Null 체크)
        String oldFilePath = existingReview.getFilePath();
        request.setFilePath(oldFilePath); // 기본적으로 이전 파일 경로를 유지

        if (file != null && !file.isEmpty()) {
            String newFilePath = minioService.handleImageUpload(file);
            request.setFilePath(newFilePath);
        }

        try {
            reviewFeignClient.updateReview(id, request, userId);
            // 업데이트 성공 후, 파일이 변경되었다면 이전 파일 삭제
            if (request.getFilePath() != null && !request.getFilePath().equals(oldFilePath) && oldFilePath != null) {
                minioService.deleteFile(oldFilePath);
            }
        } catch (Exception e) {
            // 업데이트 실패 시, 새로 업로드된 파일이 있다면 롤백(삭제)
            if (request.getFilePath() != null && !request.getFilePath().equals(oldFilePath)) {
                minioService.deleteFile(request.getFilePath());
            }
            throw new RuntimeException("리뷰 정보 업데이트에 실패했습니다.", e);
        }
    }
}