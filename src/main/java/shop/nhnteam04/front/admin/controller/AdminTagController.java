package shop.nhnteam04.front.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.admin.service.AdminTagService;
import shop.nhnteam04.front.book.domain.request.TagRequest;
import shop.nhnteam04.front.book.domain.response.TagResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class AdminTagController {

    private final AdminTagService adminTagService;

    @GetMapping
    public String allTags(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<TagResponse> tags = adminTagService.getTags(pageable);
        model.addAttribute("tags", tags);
        return "admin/tags";
    }

    @PostMapping
    public String createTag(@Valid @ModelAttribute TagRequest tagRequest, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            adminTagService.createTag(tagRequest);
            return "redirect:/admin/tags";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/tags?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/{tag-id}")
    public String deleteTag(@PathVariable(name = "tag-id") Long id) {
        try {
            adminTagService.deleteTag(id);
            return "redirect:/admin/tags";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/admin/tags?errorMessage="+errorMessage;
        }
    }
}
