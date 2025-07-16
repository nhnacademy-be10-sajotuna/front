package shop.nhnteam04.front.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @ResponseBody
    @GetMapping("/.well-known/**")
    public ResponseEntity<Void> ignore() {
        return ResponseEntity.noContent().build();  // HTTP 204
    }

}
