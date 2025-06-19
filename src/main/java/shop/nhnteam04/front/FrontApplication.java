package shop.nhnteam04.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import shop.nhnteam04.front.config.FeignAuthConfig;

@SpringBootApplication
@EnableFeignClients(
        basePackages = "shop.nhnteam04",
        defaultConfiguration = FeignAuthConfig.class
)
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }

}
