package shop.nhnteam04.front.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.nhnteam04.front.feign.account.AccountFeignClient;
import shop.nhnteam04.front.service.CookieService;
import shop.nhnteam04.front.user.response.ResponseAccessToken;

@Controller
@RequiredArgsConstructor
public class TokenController {

    private final AccountFeignClient accountFeignClient;
    private final CookieService cookieService;

    @GetMapping("/token/refresh")
    public String refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            return "/login";
        }
        try {
            String bearToken = "Bearer " + refreshToken;
            ResponseAccessToken accessToken = accountFeignClient.refreshToken(bearToken);
            ResponseCookie cookie = cookieService.getAccessTokenCookie(accessToken.getAccessToken());
            response.addHeader("Set-Cookie", cookie.toString());
        } catch (Exception e) {
            return "/login";
        }
        return "redirect:/";
    }
}
