package shop.nhnteam04.front.admin.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        try {
            if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > MAX_FILE_SIZE) {
                bindingResult.reject("file.size", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
            }
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            adminBookService.createBook(bookCreateRequest, imageFile);
            return "redirect:/admin/books";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/books/create?errorMessage="+errorMessage;
        }
    }

    @GetMapping("/edit/{book-isbn}")
    public String updateForm(@PathVariable("book-isbn") String isbn, Model model) {
        List<CategoryResponse> categories =  adminCategoryService.getAllCategories();
        BookResponse book = adminBookService.getBook(isbn);
        model.addAttribute("book", book);
        try {
            String categoriesJson = objectMapper.writeValueAsString(categories);
            model.addAttribute("allCategoriesJson", categoriesJson);
            String existingCategoriesJson = objectMapper.writeValueAsString(book.getCategories());
            model.addAttribute("existingCategoriesJson", existingCategoriesJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/book_edit";
    }

    @PostMapping("/edit/{book-isbn}")
    public String update(@PathVariable("book-isbn") String isbn,
                         @Valid @ModelAttribute BookCreateRequest bookCreateRequest,
                         BindingResult bindingResult,
                         @RequestParam(required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > MAX_FILE_SIZE) {
                bindingResult.reject("file.size", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
            }
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            adminBookService.updateBook(isbn, bookCreateRequest, imageFile);
            return "redirect:/admin/books";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/books/edit/"+ isbn  + "?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/delete/{book-isbn}")
    public String delete(@PathVariable("book-isbn") String isbn) {
        adminBookService.deleteBook(isbn);
        return "redirect:/admin/books";
    }

    @PostMapping("/import")
    public String importAladin(@RequestParam String keyword) {
        adminBookService.importBook(keyword);
        return "redirect:/admin/books";
    }
}
