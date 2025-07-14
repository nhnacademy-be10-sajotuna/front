package shop.nhnteam04.front.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.book.service.BookSearchService;
import shop.nhnteam04.front.book.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/book/search")
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService bookSearchService;
    private final CategoryService categoryService;

    @GetMapping("/keyword")
    public String searchByKeyword(@RequestParam String keyword,
                                  @RequestParam(defaultValue = "popularity") String sort,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        Page<BookSearchResponse> bookPage = bookSearchService.searchByKeyword(keyword, sort, page, size);

        model.addAttribute("books",bookPage.getContent());
        model.addAttribute("page",bookPage);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sort",sort);

        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("categories", categories);

        return "book/search";
    }

    @GetMapping("category")
    public String searchByCategory(@RequestParam String category,
                                   @RequestParam(defaultValue = "popularity") String sort,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Page<BookSearchResponse> bookPage = bookSearchService.searchByCategory(category, sort, page, size);

        model.addAttribute("books",bookPage.getContent());
        model.addAttribute("page",bookPage);
        model.addAttribute("category",category);
        model.addAttribute("sort",sort);

        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("categories", categories);

        return "book/search";
    }

    @ResponseBody
    @GetMapping("/autocomplete")
    public List<String> autoComplete(@RequestParam String keyword){
        return bookSearchService.autocomplete(keyword);
    }

}
