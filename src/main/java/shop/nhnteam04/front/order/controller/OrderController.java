package shop.nhnteam04.front.order.controller;

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
import shop.nhnteam04.front.order.dto.orders.response.BookProductResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderDetailResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderFormResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.dto.orders.request.ReturnReason;
import shop.nhnteam04.front.order.service.OrderService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    // 주문서 작성
    @GetMapping
    public ModelAndView orderForm(@AuthenticationPrincipal SecurityUser user){

        // 장바구니에 있는 상품들 가져오기
        ModelAndView mav = new ModelAndView("order/order-form");

        CartResponse userCart = cartService.getUserCart(user.getId());

        if(userCart.getItems().isEmpty()){
            return new ModelAndView("redirect:/cart");
        }
        mav.addObject("cartItems", userCart.getItems());

        OrderFormResponse orderFormResponse = orderService.getOrderForm(user.getId());
        mav.addObject("point", orderFormResponse.getPoint());
        mav.addObject("packages", orderFormResponse.getPackages());
        mav.addObject("coupons", orderFormResponse.getCoupons());
        mav.addObject("deliveryPrice", orderFormResponse.getDeliveryPrice());
        mav.addObject("userId", user.getId());
        mav.addObject("user", user);

        return mav;
    }

    @PostMapping
    public String order(@AuthenticationPrincipal SecurityUser user,
                       CreateOrderRequest request,
                       RedirectAttributes redirectAttributes) {

        if (user == null) {
            return "redirect:/login";
        }

        try {
            OrderResponse orderResponse = orderService.createOrder(user.getId(), request);

            redirectAttributes.addAttribute("orderNumber", orderResponse.getOrderNumber());
            redirectAttributes.addAttribute("finalPrice", orderResponse.getFinalPrice());

            return "redirect:/payment";
        } catch (Exception e) {
            log.error("주문 생성 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "주문 생성 중 오류가 발생했습니다.");
            return "redirect:/order";
        }
    }

    @GetMapping("/detail/{order-id}")
    public ModelAndView OrderDetail(@PathVariable("order-id") Long orderId, @AuthenticationPrincipal SecurityUser user) {
        ModelAndView mav = new ModelAndView("order/order-detail");

        OrderDetailResponse response = orderService.getOrder(orderId);

        mav.addObject("user", user);
        mav.addObject("orderDetail", response);

        List<BookProductResponse> list = orderService.getBookProducts(response.getItems());
        mav.addObject("bookProducts", list);

        return mav;
    }

    // 주문 취소
    @PostMapping("/cancel")
    public String cancelOrder(@AuthenticationPrincipal SecurityUser user,
                              @RequestParam("order-id") long orderId) {
        orderService.cancelOrder(user.getId(), orderId);

        return "redirect:/my-page/orders";
    }

    // 주문 반품
    @PostMapping("/return")
    public String returnOrder(@AuthenticationPrincipal SecurityUser user,
                              @RequestParam("order-id") long orderId,
                              @RequestParam("returnReason") ReturnReason reason) {
        orderService.returnOrder(user.getId(), orderId, reason);

        return "redirect:/my-page/orders";
    }
}
