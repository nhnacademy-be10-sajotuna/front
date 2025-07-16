package shop.nhnteam04.front.book.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.book.service.BookSearchService;
import shop.nhnteam04.front.book.service.BookService;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookSearchService bookSearchService;
    private final BookFeignClient bookFeignClient;

    @Override
    public List<BookSearchResponse> getPopularBooks(){
        return bookSearchService.getTopBooksBySort("popularity");
    }

    @Override
    public List<BookSearchResponse> getNewestBooks(){
        return bookSearchService.getTopBooksBySort("newest");
    }

    @Override
    public List<CategoryResponse> getAllSubCategories(Long id) {
        return bookFeignClient.getAllSubCategories(id);
    }
}
