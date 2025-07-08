package shop.nhnteam04.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.review.request.ReviewCreateRequest;
import shop.nhnteam04.front.review.request.ReviewUpdateRequest;
import shop.nhnteam04.front.review.response.ReviewResponse;
import shop.nhnteam04.front.review.response.ReviewStatsResponse;
import shop.nhnteam04.front.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String list(@RequestParam("isbn") String isbn, Model model) {
        List<ReviewResponse> reviews = reviewService.getReviews(isbn);
        model.addAttribute("reviews", reviews);
        model.addAttribute("isbn", isbn);
        return "review/list";
    }

    @GetMapping("/create")
    public String createForm(@RequestParam("isbn") String isbn, Model model) {
        ReviewCreateRequest req = new ReviewCreateRequest();
        req.setIsbn(isbn);
        model.addAttribute("isbn", isbn);
        model.addAttribute("reviewCreateRequest", req);
        return "review/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ReviewCreateRequest reviewCreateRequest,
                         BindingResult bindingResult,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @AuthenticationPrincipal SecurityUser user,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isbn", reviewCreateRequest.getIsbn());
            return "review/create";
        }

        long maxFileSize = 5 * 1024 * 1024; // 5MB
        if (file != null && !file.isEmpty() && file.getSize() > maxFileSize) {
            model.addAttribute("errorMessage", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
            model.addAttribute("isbn", reviewCreateRequest.getIsbn());
            return "review/create";
        }

        try {
            reviewService.createReview(reviewCreateRequest, file, user.getId());
            redirectAttributes.addFlashAttribute("message", "리뷰가 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰 등록 실패: " + e.getMessage());
        }

        return "redirect:/reviews?isbn=" + reviewCreateRequest.getIsbn();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, @RequestParam("isbn") String isbn, Model model) {
        ReviewResponse review = reviewService.getReviewById(id);
        if (review == null) {
            model.addAttribute("errorMessage", "리뷰를 찾을 수 없습니다.");
            return "redirect:/reviews?isbn=" + isbn;
        }
        model.addAttribute("review", review);
        model.addAttribute("isbn", isbn);
        model.addAttribute("reviewUpdateRequest", new ReviewUpdateRequest());
        return "review/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam("isbn") String isbn,
                       @Valid @ModelAttribute ReviewUpdateRequest reviewUpdateRequest,
                       BindingResult bindingResult,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       @RequestParam(value = "oldFilePath", required = false) String oldFilePath,
                       @AuthenticationPrincipal SecurityUser user,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        if (bindingResult.hasErrors()) {
            ReviewResponse review = reviewService.getReviewById(id);
            model.addAttribute("review", review);
            model.addAttribute("isbn", isbn);
            model.addAttribute("reviewUpdateRequest", new ReviewUpdateRequest());
            return "review/edit";
        }
        ReviewResponse review = reviewService.getReviewById(id);
        long maxFileSize = 5 * 1024 * 1024; // 5MB
        if (file != null && !file.isEmpty() && file.getSize() > maxFileSize) {
            model.addAttribute("errorMessage", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
            model.addAttribute("review", review);
            model.addAttribute("isbn", isbn);
            model.addAttribute("reviewUpdateRequest", new ReviewUpdateRequest());
            return "review/edit"; // edit 폼으로 반환
        }

        try {
            reviewService.updateReview(id, reviewUpdateRequest, file, user.getId());
            redirectAttributes.addFlashAttribute("message", "리뷰가 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰 수정 실패: " + e.getMessage());
        }

        return "redirect:/reviews?isbn=" + isbn;
    }
}
