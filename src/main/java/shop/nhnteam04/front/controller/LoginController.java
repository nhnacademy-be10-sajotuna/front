package shop.nhnteam04.front.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.request.LoginRequestUser;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequestUser loginRequestUser, HttpServletResponse httpServletResponse, Model model) {
        loginService.login(loginRequestUser, httpServletResponse);
        return "redirect:/";
    }
}
