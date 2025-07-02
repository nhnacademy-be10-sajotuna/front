package shop.nhnteam04.front.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.response.ResponseUser;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final LoginService loginService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
