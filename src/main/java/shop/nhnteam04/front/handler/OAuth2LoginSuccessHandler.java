package shop.nhnteam04.front.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import shop.nhnteam04.front.account.service.CookieService;
import shop.nhnteam04.front.feign.cart.CartFeignClient;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CookieService cookieService;
    private final CartFeignClient cartFeignClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String accessToken = (String) oAuth2User.getAttributes().get("accessToken");
        String refreshToken = (String) oAuth2User.getAttributes().get("refreshToken");
        Long userId = (Long) oAuth2User.getAttributes().get("userId");

        String guestCartId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("guestCartId".equals(cookie.getName())) {
                    guestCartId = cookie.getValue();
                    break;
                }
            }
        }

        cartFeignClient.mergeCarts(userId, guestCartId);

        ResponseCookie accessTokenCookie = cookieService.getAccessTokenCookie(accessToken);
        ResponseCookie refreshTokenCookie = cookieService.getRefreshTokenCookie(refreshToken);
        ResponseCookie deleteGuestCartCookie = cookieService.deleteGuestCartCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteGuestCartCookie.toString());


        response.sendRedirect("/");
    }
}
