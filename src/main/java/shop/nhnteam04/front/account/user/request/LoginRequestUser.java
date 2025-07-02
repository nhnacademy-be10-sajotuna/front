package shop.nhnteam04.front.account.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestUser {
    @Email(message = "이메일 형식이 아닙니다.")
    @NotNull(message = "이메일은 비어 있으면 안됩니다.")
    private String email;

    @NotNull(message = "비밀번호는 비어 있으면 안됩니다.")
    private String password;
}
