package shop.nhnteam04.front.feign.book;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/api/admin/books/{isbn}")
    void updateBook(@PathVariable String isbn, @RequestBody BookCreateRequest bookCreateRequest);

    @GetMapping("/api/admin/books/{isbn}")
    BookResponse getBook(@PathVariable String isbn);

    @DeleteMapping("/api/admin/books/{isbn}")
    void deleteBook(@PathVariable String isbn);

    @PostMapping("/api/admin/search/import")
    void importBook(@RequestParam String keyword, @RequestParam int totalPages );
}
