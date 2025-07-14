package shop.nhnteam04.front.book.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequest {

    @NotBlank(message = "태그 이름은 필수 입력 항목입니다.")
    String tagName;
}
