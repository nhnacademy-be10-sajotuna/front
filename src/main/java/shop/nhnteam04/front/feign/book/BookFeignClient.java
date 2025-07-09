package shop.nhnteam04.front.feign.book;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.nhnteam04.front.book.domain.BookCreateRequest;
import shop.nhnteam04.front.book.domain.BookResponse;
import shop.nhnteam04.front.book.domain.CategoryResponse;

import java.util.List;

@FeignClient(name= "gateway/book-api")
public interface BookFeignClient {

    @GetMapping("/api/admin/books")
    Page<BookResponse> getAllBooks(Pageable pageable);

    @GetMapping("/api/categories?page=0&size=10000")
    Page<CategoryResponse> getAllCategories();

    @PostMapping("/api/admin/books")
    void createBook(@RequestBody BookCreateRequest bookCreateRequest);

}
