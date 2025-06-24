package shop.nhnteam04.front.cart.dto;
import lombok.Data;
import java.util.List;

@Data
public class UserCartResponse implements CartResponse {
    private Long cartId;
    private Long userId;
    private List<UserCartItemResponse> items;

    @Override
    public List<UserCartItemResponse> getItems(){
        return items;
    }

    @Override
    public String Id() {
        return String.valueOf(cartId);
    }
}
