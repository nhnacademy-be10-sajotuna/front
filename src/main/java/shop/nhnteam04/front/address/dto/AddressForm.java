package shop.nhnteam04.front.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressForm {
    private long id;
    private String nickName;
    private String streetAddress;
}
