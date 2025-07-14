package shop.nhnteam04.front.feign.book;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.nhnteam04.front.book.dto.request.BookCreateRequest;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.book.dto.request.TagRequest;
import shop.nhnteam04.front.book.dto.request.CategoryCreateRequest;
import shop.nhnteam04.front.book.dto.response.TagResponse;

import java.util.List;

@FeignClient(name= "gateway/book-api")
public interface BookFeignClient {

    @GetMapping("/api/admin/books")
    Page<BookResponse> getAllBooks(Pageable pageable);

    @GetMapping("/api/categories?page=0&size=10000")
    Page<CategoryResponse> getAllCategories();

    @PostMapping("/api/admin/books")
    void createBook(@RequestBody BookCreateRequest bookCreateRequest);

    @GetMapping("/api/search/keyword")
    Page<BookSearchResponse> searchByKeyword(
     @RequestParam("keyword") String keyword,
     @RequestParam("sort") String sort,
     @RequestParam("page") int page,
     @RequestParam("size") int size
    );

    @GetMapping("/api/search/categories")
    Page<BookSearchResponse> searchByCategory(
            @RequestParam("category") String category,
            @RequestParam("sort") String sort,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @GetMapping("api/search/autocomplete")
    List<String> autoComplete(@RequestParam("keyword") String keyword);

    @PutMapping("/api/admin/books/{isbn}")
    void updateBook(@PathVariable String isbn, @RequestBody BookCreateRequest bookCreateRequest);

    @GetMapping("/api/admin/books/{isbn}")
    BookResponse getBook(@PathVariable String isbn);

    @DeleteMapping("/api/admin/books/{isbn}")
    void deleteBook(@PathVariable String isbn);

    @PostMapping("/api/admin/search/import")
    void importBook(@RequestParam String keyword, @RequestParam int totalPages );

    @GetMapping("/api/categories/children")
    Page<CategoryResponse> getAllCategoriesByParentId(Pageable pageable, @RequestParam Long parentId);

    @GetMapping("/api/categories/parents")
    List<CategoryResponse> getParentCategories(@RequestParam Long id);

    @PostMapping("/api/categories")
    void createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest);

    @DeleteMapping("/api/categories/{id}")
    void deleteCategory(@PathVariable String id);

    @GetMapping("/api/tags")
    Page<TagResponse> getAllTags(Pageable pageable);

    @PostMapping("/api/tags")
    void createTag(@RequestBody TagRequest tagRequest);

    @DeleteMapping("/api/tags/{id}")
    void deleteTag(@PathVariable Long id);
}
