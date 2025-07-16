package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookFeignClient bookFeignClient;

    public Page<BookSearchResponse> search(String keyword, String category, String sort, int page, int size){
        Page<BookSearchResponse> bookSearchResponses = bookFeignClient.searchByKeyword(keyword,category, sort, page, size);
        return bookSearchResponses;
    }

//    public Page<BookSearchResponse> searchByCategory(String category, String sort, int page, int size){
//        return bookFeignClient.searchByCategory(category, sort, page, size);
//    }

    public List<String> autocomplete(String keyword){
        return bookFeignClient.autoComplete(keyword);
    }

    public List<BookSearchResponse> getTopBooksBySort(String sort){
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookSearchResponse> page = search(null,null,sort,0,10);
        return page.getContent();
    }
}
