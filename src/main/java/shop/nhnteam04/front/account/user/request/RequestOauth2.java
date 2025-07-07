package shop.nhnteam04.front.account.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestOauth2 {
    String email;
    String name;
}
