package shop.nhnteam04.front.account.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.request.EditRequestUser;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {
    private final LoginService loginService;

    @GetMapping("/detail")
    public String me(@RequestHeader(name = "X-User-Id")Long userId ,Model model) {
        ResponseUserWithPolicy responseUserWithPolicy = loginService.detail(userId);
        model.addAttribute("user", responseUserWithPolicy);
        return "me";
    }

    @GetMapping("/edit")
    public String editForm() {
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@RequestHeader(name = "X-User-Id") Long userId, @ModelAttribute EditRequestUser editRequestUser) {
        try {
            loginService.updateUser(userId, editRequestUser);
            return "redirect:/users/me";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/users/me/edit?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestHeader(name = "X-User-Id") Long userId, HttpServletResponse httpServletResponse) {
        loginService.withdraw(userId, httpServletResponse);
        return "redirect:/login";
    }
}
