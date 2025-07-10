package shop.nhnteam04.front.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.nhnteam04.front.admin.service.AdminOrderService;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ModelAndView adminPendingOrders(Pageable pageable){
        ModelAndView mav = new ModelAndView("admin/orders");

        // 대기 중 상태인 주문들을 가져옴
        Page<OrderResponse> orders = adminOrderService.getPendingOrders(pageable);

        mav.addObject("orders", orders);

        return mav;
    }

    // 주문 배송 중으로 전환
    @PostMapping("/delivery/{order-id}")
    public String shippedOrder(@PathVariable("order-id") Long orderId){
        adminOrderService.shippedOrder(orderId);

        return "redirect:/admin/orders";
    }
}
