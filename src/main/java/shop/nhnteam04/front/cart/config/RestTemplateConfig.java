package shop.nhnteam04.front.cart.config;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

// JSESSIONID 저장 - 이걸안하면 세션을 기억하지 못해서 세션이 유지가 안됨(메서드 호출시마다 새로운 새션)
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        CookieStore cookieStore = new BasicCookieStore();

        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpclient));
    }
}
