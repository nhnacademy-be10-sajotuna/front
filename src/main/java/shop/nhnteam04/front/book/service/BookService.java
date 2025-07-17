package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

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
}
