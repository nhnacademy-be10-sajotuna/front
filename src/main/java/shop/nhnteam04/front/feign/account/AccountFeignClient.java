package shop.nhnteam04.front.feign.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.config.FeignAuthConfig;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;
import shop.nhnteam04.front.user.response.ResponseAccessToken;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

@FeignClient(name= "gateway/account-api")
public interface AccountFeignClient {
    @PostMapping("/api/users/login")
    public LoginResponse login(@RequestBody LoginRequestUser loginRequestUser);

    @GetMapping("/api/users/me")
    public ResponseUserWithPolicy me();
}
