package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;

import java.util.List;

public interface BookService {

    List<BookSearchResponse> getPopularBooks();
    List<BookSearchResponse> getNewestBooks();
    List<CategoryResponse> getAllSubCategories(Long id);
}
