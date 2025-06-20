package shop.nhnteam04.front.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.nhnteam04.front.threadLocal.TokenHolder;
import feign.codec.ErrorDecoder;
import shop.nhnteam04.front.feign.FeignErrorDecoder;

@Configuration
public class FeignAuthConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String accessToken = TokenHolder.getAccessToken();
            String refreshToken = TokenHolder.getRefreshToken();
            if (accessToken != null) {
                template.header("Authorization", "Bearer " + accessToken);
            }
            if (refreshToken != null) {
                template.header("AuthorizationRefresh", "Bearer " + refreshToken);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
