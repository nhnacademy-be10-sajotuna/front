package shop.nhnteam04.front.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.admin.service.AdminBookService;
import shop.nhnteam04.front.admin.service.AdminCategoryService;
import shop.nhnteam04.front.book.domain.BookResponse;
import shop.nhnteam04.front.book.domain.CategoryResponse;

import java.util.List;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class AdminBookController {

    private final AdminBookService adminBookService;
    private final AdminCategoryService adminCategoryService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String adminBooks(Pageable pageable, Model model) {
        Page<BookResponse> books = adminBookService.getBooks(pageable);
        model.addAttribute("books", books);
        return "admin/books";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<CategoryResponse> categories =  adminCategoryService.getAllCategories();
        try {
            String categoriesJson = objectMapper.writeValueAsString(categories); // JSON 문자열 변환
            model.addAttribute("allCategoriesJson", categoriesJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/book_create";
    }

}
