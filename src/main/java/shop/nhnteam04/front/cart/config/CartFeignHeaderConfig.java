package shop.nhnteam04.front.cart.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.nhnteam04.front.account.user.dto.SecurityUser;

@Configuration
public class CartFeignHeaderConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SecurityUser user) {
            requestTemplate.header("X-User-Id", String.valueOf(user.getId()));
        }else{
            String guestCartId = (String)request.getAttribute("guestCartId");
            if (guestCartId != null) {
                requestTemplate.header("X-Guest-Cart-Id", guestCartId);
            }
        }
    }
}
