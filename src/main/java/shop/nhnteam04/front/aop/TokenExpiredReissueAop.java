package shop.nhnteam04.front.aop;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import shop.nhnteam04.front.exception.ExpiredAccessTokenException;
import shop.nhnteam04.front.feign.account.AccountDirectFeignClient;
import shop.nhnteam04.front.service.CookieService;
import shop.nhnteam04.front.threadLocal.TokenHolder;
import shop.nhnteam04.front.user.response.ResponseAccessToken;

@Aspect
@Component
@RequiredArgsConstructor
public class TokenExpiredReissueAop {
    private final AccountDirectFeignClient accountDirectFeignClient;
    private final HttpServletResponse response;
    private final CookieService cookieService;

    @Around("@within(org.springframework.cloud.openfeign.FeignClient)")
    public Object tokenExpired(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        FeignClient annotation = target.getClass().getInterfaces()[0].getAnnotation(FeignClient.class);

        if (annotation == null || !annotation.name().contains("gateway")) {
            return joinPoint.proceed();
        }

        try {
            return joinPoint.proceed();
        } catch (ExpiredAccessTokenException e) {
            ResponseAccessToken accessToken = accountDirectFeignClient.refresh();
            TokenHolder.setAccessToken(accessToken.getAccessToken());
            ResponseCookie cookie = cookieService.getAccessTokenCookie(accessToken.getAccessToken());
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return joinPoint.proceed();
        }
    }
}
