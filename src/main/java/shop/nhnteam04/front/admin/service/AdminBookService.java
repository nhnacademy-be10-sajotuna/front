package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.domain.BookResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final BookFeignClient bookFeignClient;

    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookFeignClient.getAllBooks(pageable);
    }

}
