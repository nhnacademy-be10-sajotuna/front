package shop.nhnteam04.front.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.feign.order.OrderFeignClient;
import shop.nhnteam04.front.feign.review.ReviewFeignClient;
import shop.nhnteam04.front.review.request.ReviewCreateRequest;
import shop.nhnteam04.front.review.request.ReviewUpdateRequest;
import shop.nhnteam04.front.review.response.ReviewResponse;
import shop.nhnteam04.front.review.response.ReviewStatsResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
        return reviewFeignClient.getReviewById(id);
    }

    public void createReview(ReviewCreateRequest request, MultipartFile file, Long userId) {
        if (!orderFeignClient.isEligibleForReview(userId, request.getIsbn())) {
            throw new IllegalStateException("리뷰를 작성할 자격이 없습니다.");
        }
        if (file == null || file.isEmpty()) {
            reviewFeignClient.createReview(request, null, userId);
        } else {
            String fileName = validateImage(file);
            try {
                minioService.uploadFile(file, fileName);
                reviewFeignClient.createReview(request, fileName, userId);
            } catch (Exception e) {
                throw new RuntimeException("리뷰 등록에 실패하여 업로드된 파일을 롤백합니다.", e);
            }
        }
    }

    public void updateReview(Long id, ReviewUpdateRequest request, MultipartFile file, Long userId) {
        ReviewResponse existingReview = reviewFeignClient.getReviewById(id);
        String oldFilePath = existingReview.getFilePath();

        if (file == null || file.isEmpty()) {
            reviewFeignClient.updateReview(id, request, oldFilePath, userId);
        } else {
            String newFileName = validateImage(file);
            try {
                minioService.uploadFile(file, newFileName);
                reviewFeignClient.updateReview(id, request, newFileName, userId);
                if (oldFilePath != null && !oldFilePath.isEmpty()) {
                    minioService.deleteFile(oldFilePath);
                }
            } catch (Exception e) {
                throw new RuntimeException("리뷰 정보 업데이트에 실패하여 업로드된 파일을 롤백합니다.", e);
            }
        }
    }

    public String validateImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new IllegalArgumentException("유효하지 않은 이미지 파일입니다.");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 파일 검증 중 오류가 발생했습니다.");
        }
        return minioService.getFileName(file);
    }
}