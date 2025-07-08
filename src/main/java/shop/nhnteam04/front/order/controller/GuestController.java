package shop.nhnteam04.front.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final OrderService orderService;

    // 비회원 주문 조회 입력 창
    @GetMapping
    public String guestPage(){
        return "order/guest-page";
    }

    @PostMapping
    public String guestPost(HttpServletRequest req, RedirectAttributes re) {
        String orderNumber = req.getParameter("orderNumber");

        // TODO: orderNumber에 맞는 Order가 없다면 알림과 함께 다시 입력 창으로 간다

        re.addAttribute("orderNumber", orderNumber);
        return "redirect:/guest/order-detail";
    }

    // 주문 조회
    @GetMapping("/order-detail")
    public ModelAndView guestOrderDetail(@RequestParam String orderNumber){
        ModelAndView mav = new ModelAndView("order/guest-detail");

        OrderDetailResponse response = orderService.getGuestOrder(orderNumber);
        mav.addObject("orderDetail", response);

        return mav;
    }
}
