package shop.nhnteam04.front.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.accountFeignClient.AccountFeignClient;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AccountFeignClient accountFeignClient;

    public void login(LoginRequestUser loginRequestUser) {
       LoginResponse loginResponse = accountFeignClient.login(loginRequestUser);
       log.info("login response: {}", loginResponse);
    }
}
