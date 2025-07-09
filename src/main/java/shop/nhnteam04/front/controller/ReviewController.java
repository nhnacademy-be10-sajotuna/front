package shop.nhnteam04.front.controller;

import jakarta.validation.Valid;
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
import shop.nhnteam04.front.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

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
        model.addAttribute("reviewCreateRequest", req);
        return "review/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("reviewCreateRequest") ReviewCreateRequest reviewCreateRequest,
                         BindingResult bindingResult,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @AuthenticationPrincipal SecurityUser user,
                         RedirectAttributes redirectAttributes) {

        if (file != null && !file.isEmpty() && file.getSize() > MAX_FILE_SIZE) {
            bindingResult.reject("file.size", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "review/create";
        }

        try {
            reviewService.createReview(reviewCreateRequest, file, user);
            redirectAttributes.addFlashAttribute("message", "리뷰가 등록되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰 등록 실패: " + e.getMessage());
        }

        return "redirect:/reviews?isbn=" + reviewCreateRequest.getIsbn();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, @RequestParam("isbn") String isbn, Model model, RedirectAttributes redirectAttributes) {
        try {
            ReviewResponse review = reviewService.getReviewById(id);
            model.addAttribute("review", review);
            model.addAttribute("isbn", isbn);
            // 폼을 위한 빈 업데이트 요청 객체를 전달
            if (!model.containsAttribute("reviewUpdateRequest")) {
                model.addAttribute("reviewUpdateRequest", new ReviewUpdateRequest());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰를 불러오는 데 실패했습니다: " + e.getMessage());
            return "redirect:/reviews?isbn=" + isbn;
        }
        return "review/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam("isbn") String isbn,
                       @Valid @ModelAttribute("reviewUpdateRequest") ReviewUpdateRequest reviewUpdateRequest,
                       BindingResult bindingResult,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       @AuthenticationPrincipal SecurityUser user,
                       RedirectAttributes redirectAttributes,
                       Model model) {

        if (file != null && !file.isEmpty() && file.getSize() > MAX_FILE_SIZE) {
            bindingResult.reject("file.size", "이미지 파일 용량은 5MB를 초과할 수 없습니다.");
        }

        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 폼을 보여주기 위해 기존 리뷰 정보를 모델에 추가
            ReviewResponse review = reviewService.getReviewById(id);
            model.addAttribute("review", review);
            return "review/edit";
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