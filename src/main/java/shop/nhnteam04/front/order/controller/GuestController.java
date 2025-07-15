package shop.nhnteam04.front.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.cart.dto.response.CartResponse;
import shop.nhnteam04.front.cart.service.CartService;
import shop.nhnteam04.front.order.dto.orders.request.CreateOrderRequest;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderFormResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
@Slf4j
public class GuestController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping
    public String guestPage(){
        return "order/guest-page";
    }

    @PostMapping
    public String guestPost(HttpServletRequest req, RedirectAttributes re) {
        try{
            String orderNumber = req.getParameter("orderNumber");

            OrderDetailResponse response = orderService.getGuestOrder(orderNumber);
            if(response.getUserId() != null){
                return "redirect:/guest";
            }

            re.addAttribute("orderNumber", orderNumber);

            return "redirect:/guest/order-detail";
        } catch (Exception e){
            return "redirect:/guest";
        }
    }

    @GetMapping("/order")
    public ModelAndView guestOrderForm(@CookieValue("guestCartId") String cartId) {
        ModelAndView mav = new ModelAndView("order/guest-order-form");

        CartResponse guestCart = cartService.getGuestCart(cartId);
        mav.addObject("cartItems", guestCart.getItems());

        OrderFormResponse orderForm = orderService.getOrderForm(null);
        mav.addObject("packages", orderForm.getPackages());
        mav.addObject("deliveryPrice", orderForm.getDeliveryPrice());

        return mav;
    }

    @PostMapping("/order")
    public String order(CreateOrderRequest request,
                        RedirectAttributes redirectAttributes) {
        try {
            OrderResponse orderResponse = orderService.createOrder(null, request);

            redirectAttributes.addAttribute("orderNumber", orderResponse.getOrderNumber());
            redirectAttributes.addAttribute("finalPrice", orderResponse.getFinalPrice());

            return "redirect:/payment";
        } catch (Exception e) {
            log.error("주문 생성 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "주문 생성 중 오류가 발생했습니다.");
            return "redirect:/guest/order";
        }
    }

    // 주문 조회
    @GetMapping("/order-detail")
    public ModelAndView guestOrderDetail(@RequestParam String orderNumber){
        ModelAndView mav = new ModelAndView("order/order-detail");

        OrderDetailResponse response = orderService.getGuestOrder(orderNumber);

        mav.addObject("orderDetail", response);

        return mav;
    }
}
