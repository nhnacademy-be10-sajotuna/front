package shop.nhnteam04.front.account.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.account.user.request.EditRequestUser;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {
    private final LoginService loginService;

    @GetMapping("/detail")
    public String me(@AuthenticationPrincipal SecurityUser user, Model model) {
        ResponseUserWithPolicy responseUserWithPolicy = loginService.detail(user.getId());
        model.addAttribute("user", responseUserWithPolicy);
        return "me";
    }

    @GetMapping("/edit")
    public String editForm(@AuthenticationPrincipal SecurityUser user, Model model) {
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal SecurityUser user, @Valid @ModelAttribute EditRequestUser editRequestUser, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            loginService.updateUser(user.getId(), editRequestUser);
            return "redirect:/users/me/detail";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/users/me/edit?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal SecurityUser user, HttpServletResponse httpServletResponse) {
        loginService.withdraw(user.getId(), httpServletResponse);
        return "redirect:/login";
    }
}
