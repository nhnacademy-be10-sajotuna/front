package shop.nhnteam04.front.service;

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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            String filePath = handleImageUpload(file);
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
            String newFilePath = handleImageUpload(file);
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

    /**
     * 이미지 파일을 검증하고 스토리지에 업로드한 후, 파일 경로를 반환합니다.
     *
     * @param file 업로드할 MultipartFile
     * @return 스토리지에 저장된 파일 경로
     */
    private String handleImageUpload(MultipartFile file) {
        validateImage(file);
        String filePath = minioService.getFilePath(file);
        try {
            minioService.uploadFile(file, filePath);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    /**
     * 파일이 유효한 이미지인지 검증합니다.
     *
     * @param file 검증할 MultipartFile
     */
    private void validateImage(MultipartFile file) {
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
            throw new IllegalArgumentException("이미지 파일 검증 중 오류가 발생했습니다.", e);
        }
    }
}