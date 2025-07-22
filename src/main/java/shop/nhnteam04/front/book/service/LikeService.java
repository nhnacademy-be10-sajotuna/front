package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.request.LikeRequest;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.LikeResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BookFeignClient bookFeignClient;

    public boolean isLiked(Long userId, String isbn) {
        return bookFeignClient.checkLikeStatus(userId, isbn);
    }

    public LikeResponse addLike(Long userId, String isbn) {
        LikeRequest request = new LikeRequest(isbn);
        return bookFeignClient.addLike(userId, request);
    }

    public void removeLike(Long userId, String isbn) {
        bookFeignClient.removeLike(userId, isbn);
    }

    public List<BookResponse> getLikedBooks(Long userId) {
        return bookFeignClient.getLikedBooksByUserId(userId);
    }
}
