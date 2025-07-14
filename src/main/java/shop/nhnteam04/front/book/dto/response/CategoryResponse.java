package shop.nhnteam04.front.book.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private Long parentId;
}