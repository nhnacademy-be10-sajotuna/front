package shop.nhnteam04.front.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.nhnteam04.front.account.user.dto.PaycoUserInfoResponse;
import shop.nhnteam04.front.account.user.request.RequestOauth2;
import shop.nhnteam04.front.account.user.response.LoginResponse;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.Status;
import shop.nhnteam04.front.feign.account.AccountFeignClient;
import shop.nhnteam04.front.feign.payco.PaycoFeignClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PaycoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountFeignClient accountFeignClient;
    private final PaycoFeignClient paycoFeignClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String userInfoEndpointUri = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        String clientId = userRequest.getClientRegistration().getClientId();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        PaycoUserInfoResponse userInfoResponse = paycoFeignClient.getUserInfo(clientId, accessToken);
        PaycoUserInfoResponse.Member m = userInfoResponse.getData().getMember();
        String idNo = m.getIdNo();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("idNo", idNo);

        String email = m.getEmail();
        String name = m.getName();

        LoginResponse user = accountFeignClient.findByOutId(idNo, new RequestOauth2(email, name));

        attributes.put("accessToken", user.getAccessToken());
        attributes.put("refreshToekn", user.getRefreshToken());

        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, attributes, "idNo");
    }
}