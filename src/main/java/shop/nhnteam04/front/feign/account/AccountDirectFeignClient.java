package shop.nhnteam04.front.feign.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import shop.nhnteam04.front.user.response.ResponseAccessToken;

@FeignClient(name= "account-api")
public interface AccountDirectFeignClient {
    @PostMapping("/api/token/refresh")
    public ResponseAccessToken refresh();
}
