package shop.nhnteam04.front.accountFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;

@FeignClient(name= "gateway/account-api")
public interface AccountFeignClient {
    @PostMapping("/api/users/login")
    public LoginResponse login(@RequestBody LoginRequestUser loginRequestUser);
}
