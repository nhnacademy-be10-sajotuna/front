package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookFeignClient bookFeignClient;

    public BookResponse getBookByIsbn(String isbn) {
        return bookFeignClient.getBookByIsbn(isbn);
    }
}
