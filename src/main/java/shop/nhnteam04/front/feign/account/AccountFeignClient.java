package shop.nhnteam04.front.feign.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.nhnteam04.front.account.address.request.RequestAddress;
import shop.nhnteam04.front.account.address.response.ResponseAddress;
import shop.nhnteam04.front.account.user.request.EditRequestUser;
import shop.nhnteam04.front.account.user.request.LoginRequestUser;
import shop.nhnteam04.front.account.user.request.RegisterRequestUser;
import shop.nhnteam04.front.account.user.request.RequestOauth2;
import shop.nhnteam04.front.account.user.response.LoginResponse;
import shop.nhnteam04.front.account.user.response.ResponseAccessToken;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.ResponseUserWithPolicy;

import java.util.List;

@FeignClient(name = "gateway/account-api")
public interface AccountFeignClient {
    @PostMapping("/api/users/login")
    public LoginResponse login(@RequestBody LoginRequestUser loginRequestUser);

    @GetMapping("/api/users/me")
    public ResponseUser me(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/users/detail")
    public ResponseUserWithPolicy detail(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/token/refresh")
    public ResponseAccessToken refreshToken(@RequestHeader("AuthorizationRefresh") String token);

    @PostMapping("/api/users/logout")
    public void logout(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/users")
    public void createUser(@RequestBody RegisterRequestUser registerRequestUser);

    @PutMapping("/api/users/me")
    public void updateUser(@RequestHeader("X-User-Id") Long userId, @RequestBody EditRequestUser user);

    @DeleteMapping("/api/users/me")
    public void deleteUser(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/api/address")
    public List<ResponseAddress> getAddresses(@RequestHeader("X-User-Id") Long userId);

    @PostMapping("/api/address")
    public void saveAddress(@RequestBody RequestAddress address, @RequestHeader("X-User-Id") Long userId);

    @DeleteMapping("/api/address/{addressId}")
    public void deleteAddress(@RequestHeader("X-User-Id") Long userId, @PathVariable("addressId") Long addressId);

    @PostMapping("/api/users/oauth2/{outId}")
    public LoginResponse findByOutId(@PathVariable("outId") String outId, @RequestBody RequestOauth2 requestOauth2);

    @GetMapping("/api/token/validate")
    public ResponseUser validate(@RequestHeader("Authorization") String token);
}
