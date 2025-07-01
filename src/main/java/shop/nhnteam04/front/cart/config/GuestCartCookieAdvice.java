package shop.nhnteam04.front.cart.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.service.CookieService;

import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
public class GuestCartCookieAdvice {
    private final CookieService cookieService;
    @ModelAttribute("guestCartId")
    public String guestCartId(@RequestHeader(value = "X-Guest-Cart-Id", required = false)String cartId,
                              @RequestHeader(value = "X-User-Id", required = false) Long userId,
                              HttpServletRequest request,
                              HttpServletResponse response) {


        if (userId != null){
            request.setAttribute("guestCartId", null);
            return null;   // 회원은 쿠키 필요없음
        }

        if (cartId == null) {
            cartId = UUID.randomUUID().toString();
            ResponseCookie guestCookie = cookieService.getGuestCartIdCookie(cartId);
            response.addHeader(HttpHeaders.SET_COOKIE, guestCookie.toString());
        }
        request.setAttribute("guestCartId", cartId);
        return cartId;
    }
}
