package shop.nhnteam04.front;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final TestAdapter testAdapter;

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/account")
    public String index(Model model) {
        model.addAttribute("user", testAdapter.getUser(24L));
        return "account";
    }
}
