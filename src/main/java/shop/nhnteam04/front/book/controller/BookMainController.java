package shop.nhnteam04.front.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.book.service.BookSearchService;
import shop.nhnteam04.front.book.service.BookService;
import shop.nhnteam04.front.book.service.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookMainController {

    private final LoginService loginService;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final BookSearchService bookSearchService;

    @GetMapping("/")
    public String bookMain(Model model) {

        List<BookSearchResponse> popularBooks = bookService.getPopularBooks();

        List<BookSearchResponse> newewstBooks = bookService.getNewestBooks();


        List<BookResponse> mostLikedBooks = bookService.getMostLikedBooks();

        List<CategoryResponse> categories = categoryService.getAll();


        model.addAttribute("categories", categories);
        model.addAttribute("popularBooks", popularBooks);
        model.addAttribute("newestBooks", newewstBooks);
        model.addAttribute("mostLikedBooks", mostLikedBooks);

        return "index";
    }

    @GetMapping("/categories/{id}/subcategories")
    @ResponseBody
    public List<CategoryResponse> getAllSubCategories(@PathVariable Long id) {
        List<CategoryResponse> allSubCategories = bookService.getAllSubCategories(id);
        return allSubCategories;
    }

    @GetMapping("/api/categories/all")
    @ResponseBody
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAll();
    }

}
