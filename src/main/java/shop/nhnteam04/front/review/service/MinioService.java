package shop.nhnteam04.front.review.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

@Service
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String getFilePath(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }

    public void uploadFile(MultipartFile file, String filePath) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    public void deleteFile(String objectPath) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectPath.startsWith("/") ? objectPath.substring(1 + bucket.length()) : objectPath)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }
    /**
     * 이미지 파일을 검증하고 스토리지에 업로드한 후, 파일 경로를 반환합니다.
     *
     * @param file 업로드할 MultipartFile
     * @return 스토리지에 저장된 파일 경로
     */
    public String handleImageUpload(MultipartFile file) {
        validateImage(file);
        String filePath = getFilePath(file);
        uploadFile(file, filePath);
        return filePath;
    }

    /**
     * 파일이 유효한 이미지인지 검증합니다.
     *
     * @param file 검증할 MultipartFile
     */
    public void validateImage(MultipartFile file) {
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
