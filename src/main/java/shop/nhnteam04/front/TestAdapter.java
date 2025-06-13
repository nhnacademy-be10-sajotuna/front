package shop.nhnteam04.front;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.user.response.ResponseUser;

@FeignClient(name = "gateway", contextId = "testAdapter")
public interface TestAdapter {

    @GetMapping("/order-api/")
    String test();

    @GetMapping("/account-api/api/users/me")
    ResponseUser getUser(@RequestHeader("X-User-Id") Long userId);

}
