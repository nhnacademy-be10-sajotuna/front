package shop.nhnteam04.front.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.request.RegisterRequestUser;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "errorMessage", required = false) String errorMessage,
                            Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequestUser loginRequestUser, HttpServletResponse httpServletResponse) {
        try {
            loginService.login(loginRequestUser, httpServletResponse);
            return "redirect:/";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/login?errorMessage=" + errorMessage;
        }
    }

    @GetMapping("/register")
    public String registerForm(@RequestParam(value = "errorMessage", required = false) String errorMessage,
                               Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequestUser registerRequestUser) {
        try {
            loginService.register(registerRequestUser);
            return "redirect:/login";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/register?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader(name = "X-User-Id") Long userId, HttpServletResponse httpServletResponse) {
        loginService.logout(userId, httpServletResponse);
        return "redirect:/";
    }

    @GetMapping("/users/me")
    public String me(@RequestHeader(name = "X-User-Id") Long userId, Model model) {
        ResponseUserWithPolicy responseUserWithPolicy = loginService.me(userId);
        model.addAttribute("user", responseUserWithPolicy);
        return "me";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestHeader(name = "X-User-Id") Long userId, HttpServletResponse httpServletResponse) {
        loginService.withdraw(userId, httpServletResponse);
        return "redirect:/login";
    }

}
