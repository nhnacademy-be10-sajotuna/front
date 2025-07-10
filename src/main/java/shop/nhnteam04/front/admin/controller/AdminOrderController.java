package shop.nhnteam04.front.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.admin.service.AdminOrderService;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ModelAndView adminPendingOrders(Pageable pageable, @RequestParam(required = false) String status){
        ModelAndView mav = new ModelAndView("admin/orders");

        Page<OrderResponse> orders;

        if(status.isEmpty() || status.equals("ALL")){
            mav.addObject("status", "ALL");
            orders = adminOrderService.getAllOrders(pageable);
        } else {
            mav.addObject("status", status);
            orders = adminOrderService.getStatusOrders(status, pageable);
        }
        mav.addObject("orders", orders);

        return mav;
    }

    // 주문 배송 중으로 전환
    @PostMapping("/delivery/{order-id}")
    public String shippedOrder(@PathVariable("order-id") Long orderId, RedirectAttributes re){
        try {
            adminOrderService.shippedOrder(orderId);
            re.addFlashAttribute("successMessage", "주문이 배송 중으로 변경되었습니다.");
        } catch (Exception e) {
            re.addFlashAttribute("errorMessage", "주문 상태 변경 중 오류가 발생했습니다.");
        }

        return "redirect:/admin/orders?status=PENDING";
    }
}
