package shop.nhnteam04.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.TestAdapter;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.response.ResponseUser;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final LoginService loginService;

    @ModelAttribute
    public void addUserToModel(@RequestHeader(name = "X-User-Id", required = false)Long userId ,Model model) {
        if (userId != null) {
            ResponseUser responseUser = loginService.me(userId);
            model.addAttribute("user", responseUser);
        }
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


}
