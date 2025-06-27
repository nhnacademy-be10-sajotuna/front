package shop.nhnteam04.front.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.account.AccountFeignClient;
import shop.nhnteam04.front.user.request.EditRequestUser;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.request.RegisterRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final AccountFeignClient accountFeignClient;
    private final CookieService cookieService;

    public void login(LoginRequestUser loginRequestUser, HttpServletResponse response) {
       LoginResponse loginResponse = accountFeignClient.login(loginRequestUser);
       log.info("login response: {}", loginResponse);

        ResponseCookie accessTokenCookie = cookieService.getAccessTokenCookie(loginResponse.getAccessToken());
        ResponseCookie refreshTokenCookie = cookieService.getRefreshTokenCookie(loginResponse.getRefreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    public ResponseUserWithPolicy me(long userId) {
        return accountFeignClient.me(userId);
    }

    public void logout(Long userId, HttpServletResponse response) {
        accountFeignClient.logout(userId);

        ResponseCookie accessDelete = cookieService.deleteAccessTokenCookie();
        ResponseCookie refreshDelete = cookieService.deleteRefreshTokenCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, accessDelete.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshDelete.toString());
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
    }
}
