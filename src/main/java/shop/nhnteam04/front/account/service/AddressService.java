package shop.nhnteam04.front.account.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.account.address.dto.AddressForm;
import shop.nhnteam04.front.account.address.request.RequestAddress;
import shop.nhnteam04.front.account.address.response.ResponseAddress;
import shop.nhnteam04.front.feign.account.AccountFeignClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AccountFeignClient accountFeignClient;
    private final ObjectMapper objectMapper;

    public List<AddressForm> getAddresses(Long userId) {
        List<AddressForm> addresses = new ArrayList<>();
        List<ResponseAddress> addressList = accountFeignClient.getAddresses(userId);
        for (ResponseAddress address : addressList) {
            AddressForm addressForm = objectMapper.convertValue(address, AddressForm.class);
            addresses.add(addressForm);
        }
        return addresses;
    }

    public void createAddress(Long userId, AddressForm addressForm) {
        RequestAddress address = objectMapper.convertValue(addressForm, RequestAddress.class);
        accountFeignClient.saveAddress(address, userId);
    }

    public void deleteAddress(Long userId, Long addressId) {
        accountFeignClient.deleteAddress(userId, addressId);
    }

}
