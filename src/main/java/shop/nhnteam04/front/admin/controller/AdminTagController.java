package shop.nhnteam04.front.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.admin.service.AdminTagService;
import shop.nhnteam04.front.book.domain.request.TagRequest;
import shop.nhnteam04.front.book.domain.response.TagResponse;

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
    public String createTag(@ModelAttribute TagRequest tagRequest) {
        adminTagService.createTag(tagRequest);
        return "redirect:/admin/tags";
    }

    @PostMapping("/{tag-id}")
    public String deleteTag(@PathVariable(name = "tag-id") Long id) {
        adminTagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
