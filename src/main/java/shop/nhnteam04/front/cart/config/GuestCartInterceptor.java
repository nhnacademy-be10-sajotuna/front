package shop.nhnteam04.front.cart.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.nhnteam04.front.account.service.CookieService;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GuestCartInterceptor implements HandlerInterceptor {
    private final CookieService cookieService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && auth instanceof UsernamePasswordAuthenticationToken;
        if (isLoggedIn) return true;

        boolean hasGuestCart = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .anyMatch(c -> "guestCartId".equals(c.getName()));
        if (!hasGuestCart) {
            String guestCartId = UUID.randomUUID().toString();
            ResponseCookie guestCartCookie = cookieService.getGuestCartIdCookie(guestCartId);
            response.addHeader(HttpHeaders.SET_COOKIE, guestCartCookie.toString());
        }

        return true;
    }
}
