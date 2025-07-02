package shop.nhnteam04.front.feign.payco;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.nhnteam04.front.account.user.dto.PaycoUserInfoResponse;

@FeignClient(name = "paycoFeignClient", url = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json")
public interface PaycoFeignClient {
    @PostMapping
    public PaycoUserInfoResponse getUserInfo(@RequestHeader("client_id") String clientId, @RequestHeader("access_token") String accessToken);

}

