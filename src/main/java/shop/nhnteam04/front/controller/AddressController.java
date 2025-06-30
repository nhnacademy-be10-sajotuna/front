package shop.nhnteam04.front.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.address.dto.AddressForm;
import shop.nhnteam04.front.address.request.RequestAddress;
import shop.nhnteam04.front.service.LoginService;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;
import shop.nhnteam04.front.service.AddressService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final LoginService loginService;
    private final AddressService addressService;

    @ModelAttribute("user")
    public ResponseUserWithPolicy user(@RequestHeader("X-User-Id")Long userId) {
        return loginService.detail(userId);
    }

    @GetMapping("/address")
    public String address(@ModelAttribute("user") ResponseUserWithPolicy user, Model model) {
       List<AddressForm> addressForms = addressService.getAddresses(user.getId());
       model.addAttribute("addressList", addressForms);
       return "address";
    }

    @PostMapping("/address")
    public String createAddress(@ModelAttribute("user") ResponseUserWithPolicy user, @Valid @ModelAttribute AddressForm address, RedirectAttributes redirectAttributes) {
        try {
            addressService.createAddress(user.getId(), address);
            return "redirect:/address";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/address";
        }
    }

    @PostMapping("/address/{addressId}")
    public String deleteAddress(@ModelAttribute("user") ResponseUserWithPolicy user, @PathVariable Long addressId, Model model) {
        addressService.deleteAddress(user.getId(), addressId);
        return "redirect:/address";
    }
}
