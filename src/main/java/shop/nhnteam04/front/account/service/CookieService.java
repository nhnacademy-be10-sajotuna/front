package shop.nhnteam04.front.account.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {


    private static final Long ACCESS_TOKEN_EXPIRES = 1800L;
    private static final Long REFRESH_TOKEN_EXPIRES = 24 * 60 * 60L;
    private static final Long GUEST_TOKEN_EXPIRES = 60 * 60 * 24L;

    @Value("${cookie.domain:}")
    private String cookieDomain;
    @Value("${cookie.secure:false}")
    private boolean cookieSecure;

    public ResponseCookie getAccessTokenCookie(String token) {
        return getResponseCookie("access_token", token, ACCESS_TOKEN_EXPIRES);
    }

    public ResponseCookie getRefreshTokenCookie(String token) {
        return getResponseCookie("refresh_token", token, REFRESH_TOKEN_EXPIRES);
    }

    public ResponseCookie getGuestCartIdCookie(String cartId) {
        return getResponseCookie("guestCartId", cartId, GUEST_TOKEN_EXPIRES);
    }


    private ResponseCookie getResponseCookie(String tokenName, String token, Long tokenExpires) {
        return ResponseCookie.from(tokenName, token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .domain(cookieDomain)
                .maxAge(tokenExpires)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie deleteAccessTokenCookie() {
        return deleteCookie("access_token");
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return deleteCookie("refresh_token");
    }

    public ResponseCookie deleteGuestCartCookie() {return deleteCookie("guestCartId");}

    private ResponseCookie deleteCookie(String tokenName) {
        return ResponseCookie.from(tokenName, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .domain(cookieDomain)
                .maxAge(0) // 💡 쿠키 즉시 삭제
                .sameSite("Lax")
                .build();
    }
}
