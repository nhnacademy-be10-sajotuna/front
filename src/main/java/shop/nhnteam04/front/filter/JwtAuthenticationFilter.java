package shop.nhnteam04.front.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.nhnteam04.front.account.service.CookieService;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.account.user.response.ResponseAccessToken;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.feign.account.AccountFeignClient;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LoginService loginService;
    private final AccountFeignClient accountFeignClient;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveAccessTokenFromCookie(request);
        String refreshToken = resolveRefreshTokenFromCookie(request);

        if (accessToken != null) {
            String bearAccessToken = "Bearer " + accessToken;
            ResponseUser responseUser = accountFeignClient.validate(bearAccessToken);
            if (responseUser != null) {
                SecurityUser securityUser = SecurityUser.from(responseUser);
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
                return;
            }
        }

        if (refreshToken != null) {
            String bearRefreshToken = "Bearer " + refreshToken;
            ResponseAccessToken responseAccessToken = accountFeignClient.refreshToken(bearRefreshToken);
            if (responseAccessToken != null) {
                ResponseCookie cookie = cookieService.getAccessTokenCookie(responseAccessToken.getAccessToken());
                response.addHeader("Set-Cookie", cookie.toString());

                SecurityUser securityUser = SecurityUser.from(responseAccessToken.getResponseUser());
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessTokenFromCookie(HttpServletRequest request) {
        return resolveTokenFromCookie(request, "access_token");
    }

    private String resolveRefreshTokenFromCookie(HttpServletRequest request) {
        return resolveTokenFromCookie(request, "refresh_token");
    }


    private String resolveTokenFromCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }




}
