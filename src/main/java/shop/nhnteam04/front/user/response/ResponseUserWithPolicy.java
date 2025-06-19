package shop.nhnteam04.front.user.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import shop.nhnteam04.front.user.dto.UserGradePolicyDto;


@Data
@NoArgsConstructor
public class ResponseUserWithPolicy {
    private long id;

    private UserGradePolicyDto userGradePolicyDto;

    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Status status;
    private AuthType authType;
    private LocalDateTime currentLoginAt;
}
