package shop.nhnteam04.front.account.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import shop.nhnteam04.front.account.user.response.AuthType;
import shop.nhnteam04.front.account.user.response.Role;
import shop.nhnteam04.front.account.user.response.Status;

import java.time.LocalDate;

@Data
public class EditRequestUser {
    @NotNull(message = "이름은 비어 있으면 안됩니다.")
    @Size(min = 1, max = 10, message = "이름은 1자리 이상 10자리 이하 입니다.")
    private String name;
    @Size(min = 3, message = "비밀번호는 3자리 이상입니다.")
    private String password;
    @NotNull(message = "번호는 비어 있으면 안됩니다.")
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생일을 입력하셔야 합니다.")
    private LocalDate birthDate;
}
