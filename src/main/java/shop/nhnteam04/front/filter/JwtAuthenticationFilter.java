package shop.nhnteam04.front.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.nhnteam04.front.account.service.LoginService;
import shop.nhnteam04.front.account.user.dto.SecurityUser;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.tokenParser.JwtTokenValidator;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveAccessTokenFromCookie(request);

        if (jwtTokenValidator.validate(accessToken)) {
            String userId = jwtTokenValidator.getIdFromToken(accessToken);

            ResponseUser responseUser = loginService.me(Long.parseLong(userId));

            SecurityUser securityUser = new SecurityUser(responseUser.getId(), responseUser.getName(), responseUser.getEmail(), responseUser.getRole());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
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
