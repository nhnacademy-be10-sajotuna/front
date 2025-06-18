package shop.nhnteam04.front.user.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseUser {
    private long id;

    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Status status;
    private AuthType authType;
    private LocalDateTime currentLoginAt;
}
