package shop.nhnteam04.front.account.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonCreator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccessToken {
    private String accessToken;
    private ResponseUser responseUser;
}
