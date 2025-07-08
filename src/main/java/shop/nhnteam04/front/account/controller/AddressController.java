//package shop.nhnteam04.front.account.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import shop.nhnteam04.front.account.address.dto.AddressForm;
//import shop.nhnteam04.front.account.service.LoginService;
//import shop.nhnteam04.front.account.user.dto.SecurityUser;
//import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;
//import shop.nhnteam04.front.account.service.AddressService;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/address")
//public class AddressController {
//
//    private final LoginService loginService;
//    private final AddressService addressService;
//
//    @GetMapping
//    public String address(@AuthenticationPrincipal SecurityUser user, Model model) {
//       List<AddressForm> addressForms = addressService.getAddresses(user.getId());
//       model.addAttribute("addressList", addressForms);
//       return "address";
//    }
//
//    @PostMapping
//    public String createAddress(@AuthenticationPrincipal SecurityUser user, @Valid @ModelAttribute AddressForm address, RedirectAttributes redirectAttributes) {
//        try {
//            addressService.createAddress(user.getId(), address);
//            return "redirect:/address";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:/address";
//        }
//    }
//
//    @PostMapping("/{addressId}")
//    public String deleteAddress(@AuthenticationPrincipal SecurityUser user, @PathVariable Long addressId, Model model) {
//        addressService.deleteAddress(user.getId(), addressId);
//        return "redirect:/address";
//    }
//}
