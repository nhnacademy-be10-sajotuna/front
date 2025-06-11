package shop.nhnteam04.front;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway", contextId = "testAdapter")
public interface TestAdapter {

    @GetMapping("/order-api/")
    String test();
}
