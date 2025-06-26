package shop.nhnteam04.front.address.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAddress {
    private long id;
    private String nickName;
    private String streetAddress;
}
