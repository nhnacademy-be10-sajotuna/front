package shop.nhnteam04.front.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.accountFeignClient.AccountFeignClient;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AccountFeignClient accountFeignClient;
    private static final Long ACCESS_TOKEN_EXPIRES = 1800 * 1000L;
    private static final Long REFRESH_TOKEN_EXPIRES = 24 * 60 * 60 * 1000L;

    public void login(LoginRequestUser loginRequestUser, HttpServletResponse response) {
       LoginResponse loginResponse = accountFeignClient.login(loginRequestUser);
       log.info("login response: {}", loginResponse);

        ResponseCookie accessTokenCookie = getResponseCookie("access_token", loginResponse.getAccessToken(), ACCESS_TOKEN_EXPIRES);

        ResponseCookie refreshTokenCookie = getResponseCookie("refresh_token", loginResponse.getRefreshToken(), REFRESH_TOKEN_EXPIRES);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    private ResponseCookie getResponseCookie(String tokenName, String token, Long tokenExpires) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(tokenName, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .domain("nhn-team04.shop")
                .maxAge(tokenExpires)
                .sameSite("Lax")
                .build();
        return accessTokenCookie;
    }
}
