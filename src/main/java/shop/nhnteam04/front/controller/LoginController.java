package shop.nhnteam04.front.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.request.RegisterRequestUser;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequestUser loginRequestUser, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) {
        try {
            loginService.login(loginRequestUser, httpServletResponse);
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequestUser registerRequestUser, RedirectAttributes redirectAttributes) {
        try {
            loginService.register(registerRequestUser);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
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
