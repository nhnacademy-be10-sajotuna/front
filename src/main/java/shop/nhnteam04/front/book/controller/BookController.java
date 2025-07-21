package shop.nhnteam04.front.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.book.dto.response.BookResponse;
import shop.nhnteam04.front.book.dto.response.BookSearchResponse;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.book.service.BookService;
import shop.nhnteam04.front.book.service.BookSearchService;
import shop.nhnteam04.front.book.service.LikeService;
import shop.nhnteam04.front.review.response.ReviewResponse;
import shop.nhnteam04.front.review.service.ReviewService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/book/detail")
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final LikeService likeService;

    @GetMapping("/{book-isbn}")
    public String book(@AuthenticationPrincipal SecurityUser user, @PathVariable("book-isbn") String isbn, Model model) {
        BookResponse book = bookService.getBookByIsbn(isbn);
        List<ReviewResponse> reviews = reviewService.getReviewsByBook(isbn);
        model.addAttribute("reviews", reviews);
        model.addAttribute("isbn", isbn);
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        if (user != null) {
            model.addAttribute("isLike", likeService.isLiked(user.getId(), isbn));
        }
        Set<Long> categoryIds = book.getCategories().stream().flatMap(List::stream).map(CategoryResponse::getId).collect(Collectors.toSet());
        model.addAttribute("categoryIds", categoryIds);
        return "book/book";
    }
}
