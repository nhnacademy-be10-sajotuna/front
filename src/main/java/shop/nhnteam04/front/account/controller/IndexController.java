package shop.nhnteam04.front.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @ResponseBody
    @GetMapping("/.well-known/**")
    public ResponseEntity<Void> ignore() {
        return ResponseEntity.noContent().build();  // HTTP 204
    }

}
