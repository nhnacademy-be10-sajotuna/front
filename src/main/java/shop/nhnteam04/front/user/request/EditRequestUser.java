package shop.nhnteam04.front.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditRequestUser {
    @NotNull(message = "이름은 비어 있으면 안됩니다.")
    private String name;
    private String password;
    @NotNull(message = "번호는 비어 있으면 안됩니다.")
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생일을 입력하셔야 합니다.")
    private LocalDate birthDate;
}
