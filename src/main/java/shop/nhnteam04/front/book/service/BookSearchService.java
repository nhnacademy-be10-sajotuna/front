package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookFeignClient bookFeignClient;

    public Page<BookSearchResponse> searchByKeyword(String keyword, String sort, int page, int size){
        Page<BookSearchResponse> bookSearchResponses = bookFeignClient.searchByKeyword(keyword, sort, page, size);
        return bookSearchResponses;
    }

    public Page<BookSearchResponse> searchByCategory(String category, String sort, int page, int size){
        return bookFeignClient.searchByCategory(category, sort, page, size);
    }

    public List<String> autocomplete(String keyword){
        return bookFeignClient.autoComplete(keyword);
    }
}
