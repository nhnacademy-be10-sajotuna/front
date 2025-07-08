package shop.nhnteam04.front.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @GetMapping("/images/{fileName}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String fileName) {
        try {
            // MinIO에서 파일 InputStream을 가져옵니다.
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build());

            // 이미지 타입에 맞는 Content-Type과 함께 데이터를 반환합니다.
            // 실제 Content-Type을 저장하고 사용하면 더 좋습니다.
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 기본을 JPEG로 설정, PNG 등 다른 포맷도 고려
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            // 파일을 찾지 못하거나 오류 발생 시 404 응답
            return ResponseEntity.notFound().build();
        }
    }
}