package shop.nhnteam04.front.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.admin.service.AdminBookService;
import shop.nhnteam04.front.admin.service.AdminCategoryService;
import shop.nhnteam04.front.book.domain.BookCreateRequest;
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
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @GetMapping
    public String adminBooks(Pageable pageable, Model model) {
        Page<BookResponse> books = adminBookService.getBooks(pageable);
        model.addAttribute("books", books);
        return "admin/books";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        List<CategoryResponse> categories =  adminCategoryService.getAllCategories();
        try {
            String categoriesJson = objectMapper.writeValueAsString(categories); // JSON 문자열 변환
            model.addAttribute("allCategoriesJson", categoriesJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/book_create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute BookCreateRequest bookCreateRequest,
                         BindingResult bindingResult,
                         @RequestParam(required = false) MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > MAX_FILE_SIZE) {
            bindingResult.reject("file.size", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
        }
        if (bindingResult.hasErrors()) {
            return "admin/book_create";
        }
        adminBookService.createBook(bookCreateRequest, imageFile);

        return "redirect:/admin/books";
    }


}
