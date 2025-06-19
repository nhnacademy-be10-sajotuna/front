package shop.nhnteam04.front.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.nhnteam04.front.threadLocal.TokenHolder;

@Configuration
public class FeignAuthConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String token = TokenHolder.get();
            if (token != null) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}
