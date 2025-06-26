package shop.nhnteam04.front.feign.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.address.request.RequestAddress;
import shop.nhnteam04.front.address.response.ResponseAddress;
import shop.nhnteam04.front.user.request.LoginRequestUser;
import shop.nhnteam04.front.user.request.RegisterRequestUser;
import shop.nhnteam04.front.user.response.LoginResponse;
import shop.nhnteam04.front.user.response.ResponseAccessToken;
import shop.nhnteam04.front.user.response.ResponseUserWithPolicy;

import java.util.List;

@FeignClient(name= "account-api")
public interface AccountFeignClient {
    @PostMapping("/api/users/login")
    public LoginResponse login(@RequestBody LoginRequestUser loginRequestUser);

    @GetMapping("/api/users/me")
    public ResponseUserWithPolicy me(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/token/refresh")
    public ResponseAccessToken refreshToken(@RequestHeader("AuthorizationRefresh") String token);

    @PostMapping("/api/users/logout")
    public void logout(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/users")
    public void createUser(@RequestBody RegisterRequestUser registerRequestUser);

    @DeleteMapping("/api/users/me")
    public void deleteUser(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/address")
    public List<ResponseAddress> getAddresses(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/address")
    public void saveAddress(@RequestBody RequestAddress address, @RequestHeader("X-User-Id") Long userId);

    @DeleteMapping("/api/address/{addressId}")
    public void deleteAddress(@RequestHeader("X-User-Id") Long userId, @PathVariable("addressId") Long addressId);
}
