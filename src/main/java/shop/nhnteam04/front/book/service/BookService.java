package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookFeignClient bookFeignClient;
    private final BookSearchService bookSearchService;

    public BookResponse getBookByIsbn(String isbn) {
        return bookFeignClient.getBookByIsbn(isbn);
    }

    public List<BookSearchResponse> getPopularBooks(){
        return bookSearchService.getTopBooksBySort("popularity");
    }

    public List<BookSearchResponse> getNewestBooks(){
        return bookSearchService.getTopBooksBySort("newest");
    }

    public List<CategoryResponse> getAllSubCategories(Long id) {
        return bookFeignClient.getAllSubCategories(id);
    }

    public List<BookResponse> getMostLikedBooks() {
        try {
            // 좋아요 많은 책 5개만 가져오기 (메인 배너용)
            Pageable pageable = PageRequest.of(0, 5);
            return bookFeignClient.getBooksByLikesDesc(pageable).getContent();
        } catch (Exception e) {
            // Book-API 연결 실패하거나 데이터가 없어도 메인페이지는 정상 동작
            System.out.println("독자 추천도서 조회 실패: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
