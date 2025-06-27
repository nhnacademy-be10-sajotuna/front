package shop.nhnteam04.front.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute
    public void addErrorMessageToModel(@RequestParam(value = "errorMessage", required = false) String errorMessage, Model model) {
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
    }
}
