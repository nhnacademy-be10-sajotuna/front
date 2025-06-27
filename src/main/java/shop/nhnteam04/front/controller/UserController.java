package shop.nhnteam04.front.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.request.EditRequestUser;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {
    private final LoginService loginService;

    @ModelAttribute
    public void addUserToModel(@RequestHeader(name = "X-User-Id")Long userId ,Model model) {
        if (userId != null) {
            ResponseUserWithPolicy responseUserWithPolicy = loginService.me(userId);
            model.addAttribute("user", responseUserWithPolicy);
        }
    }

    @GetMapping
    public String me() {
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
