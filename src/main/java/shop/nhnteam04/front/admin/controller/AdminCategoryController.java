package shop.nhnteam04.front.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.admin.service.AdminCategoryService;
import shop.nhnteam04.front.book.domain.CategoryCreateRequest;
import shop.nhnteam04.front.book.domain.CategoryResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @GetMapping
    public String getCategories(Pageable pageable,
                                @RequestParam(required = false) Long parentId,
                                Model model) {
        Page<CategoryResponse> categories = adminCategoryService.getAllCategoriesByParentId(pageable, parentId);
        model.addAttribute("parentId", parentId);
        if (parentId != null) {
            List<CategoryResponse> parentCategories = adminCategoryService.getParentCategories(parentId);
            model.addAttribute("parentCategories", parentCategories);
        }
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryCreateRequest categoryCreateRequest,
                                 BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getAllErrors().get(0).toString());
            }
            adminCategoryService.createCategory(categoryCreateRequest);
            return "redirect:/admin/categories?parentId=" + categoryCreateRequest.getParentCategoryId();
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/categories?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/{category-id}")
    public String deleteCategory(@PathVariable(name = "category-id") String categoryId) {
        try {
            adminCategoryService.deleteCategory(categoryId);
            return "redirect:/admin/categories";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/categories?errorMessage="+errorMessage;
        }
    }
}
