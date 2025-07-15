package shop.nhnteam04.front.account.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import shop.nhnteam04.front.feign.account.AccountFeignClient;
import shop.nhnteam04.front.account.user.request.EditRequestUser;
import shop.nhnteam04.front.account.user.request.LoginRequestUser;
import shop.nhnteam04.front.account.user.request.RegisterRequestUser;
import shop.nhnteam04.front.account.user.response.LoginResponse;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;
import shop.nhnteam04.front.feign.cart.CartFeignClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final AccountFeignClient accountFeignClient;
    private final CookieService cookieService;
    private final CartFeignClient cartFeignClient;

    public void login(LoginRequestUser loginRequestUser, HttpServletResponse response) {
       LoginResponse loginResponse = accountFeignClient.login(loginRequestUser);
       log.info("login response: {}", loginResponse);

        ResponseCookie accessTokenCookie = cookieService.getAccessTokenCookie(loginResponse.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieService.getRefreshTokenCookie(loginResponse.getRefreshToken());

        ResponseCookie deleteGuestCartCookie = cookieService.deleteGuestCartCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteGuestCartCookie.toString());
    }

    public ResponseUser me(long userId) {
        return accountFeignClient.me(userId);
    }

    public ResponseUserWithPolicy detail(long userId) {
        return accountFeignClient.detail(userId);
    }

    public void logout(Long userId, HttpServletResponse response) {
        accountFeignClient.logout(userId);

        ResponseCookie accessDelete = cookieService.deleteAccessTokenCookie();
        ResponseCookie refreshDelete = cookieService.deleteRefreshTokenCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, accessDelete.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshDelete.toString());

        SecurityContextHolder.clearContext();
    }

    public void register(RegisterRequestUser registerRequestUser) {
        accountFeignClient.createUser(registerRequestUser);
    }

    public void updateUser(Long userId, EditRequestUser editRequestUser) {
        accountFeignClient.updateUser(userId, editRequestUser);
    }

    public void withdraw(Long userId, HttpServletResponse response) {
        accountFeignClient.deleteUser(userId);

        ResponseCookie accessDelete = cookieService.deleteAccessTokenCookie();
        ResponseCookie refreshDelete = cookieService.deleteRefreshTokenCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, accessDelete.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshDelete.toString());

        SecurityContextHolder.clearContext();
    }
}
