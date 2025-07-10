package shop.nhnteam04.front.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.admin.service.AdminPackageService;
import shop.nhnteam04.front.order.dto.orders.request.PackageRequest;
import shop.nhnteam04.front.order.dto.orders.response.PackageResponse;

import java.util.List;

@Controller
@RequestMapping("/admin/packages")
@RequiredArgsConstructor
public class AdminPackageController {

    private final AdminPackageService adminPackageService;

    @GetMapping
    public ModelAndView adminPackagePage(){
        ModelAndView mvc = new ModelAndView("admin/packages");

        List<PackageResponse> responses = adminPackageService.getPackageList();

        mvc.addObject("packages", responses);

        return mvc;
    }

    @GetMapping("/create")
    public String createPackagePage(){
        return "admin/packages-create";
    }

    @PostMapping("/create")
    public String createPackage(@ModelAttribute PackageRequest packageRequest){
        adminPackageService.createPackage(packageRequest);

        return "redirect:/admin/packages";
    }

    @PostMapping("/delete/{package-id}")
    public String deletePackage(@PathVariable("package-id") long packageId){
        adminPackageService.deletePackage(packageId);

        return "redirect:/admin/packages";
    }
}
