package shop.nhnteam04.front.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.book.dto.request.LikeRequest;
import shop.nhnteam04.front.book.dto.response.LikeResponse;
import shop.nhnteam04.front.book.service.LikeService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponse> addLike(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody LikeRequest likeRequest) {
        try {
            LikeResponse response = likeService.addLike(userId, likeRequest.getBookIsbn());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("좋아요 추가 실패: userId={}, isbn={}", userId, likeRequest.getBookIsbn(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam String bookIsbn) {
        try {
            likeService.removeLike(userId, bookIsbn);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("좋아요 삭제 실패: userId={}, isbn={}", userId, bookIsbn, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLikeStatus(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam String bookIsbn) {
        try {
            boolean isLiked = likeService.isLiked(userId, bookIsbn);
            return ResponseEntity.ok(isLiked);
        } catch (Exception e) {
            log.error("좋아요 상태 확인 실패: userId={}, isbn={}", userId, bookIsbn, e);
            return ResponseEntity.badRequest().build();
        }
    }
}