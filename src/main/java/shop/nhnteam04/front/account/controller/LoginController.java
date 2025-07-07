package shop.nhnteam04.front.account.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.request.LoginRequestUser;
import shop.nhnteam04.front.account.user.request.RegisterRequestUser;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.account.user.response.AuthType;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequestUser loginRequestUser, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            loginService.login(loginRequestUser, httpServletResponse);
            return "redirect:/";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/login?errorMessage=" + errorMessage;
        }
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequestUser registerRequestUser, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            }
            loginService.register(registerRequestUser);
            return "redirect:/login";
        } catch (Exception e) {
            String errorMessage = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/register?errorMessage="+errorMessage;
        }
    }

    @PostMapping("/logout")
    public String logout(@AuthenticationPrincipal SecurityUser user, HttpServletResponse httpServletResponse) {
        loginService.logout(user.getId(), httpServletResponse);
        return "redirect:/";
    }

}
