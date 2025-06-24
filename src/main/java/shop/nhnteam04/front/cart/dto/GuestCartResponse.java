package shop.nhnteam04.front.cart.dto;
import lombok.Data;
import java.util.List;

@Data
public class GuestCartResponse implements CartResponse {
    private String sessionId;
    private List<GuestCartItemResponse> items;

    @Override
    public List<GuestCartItemResponse> getItems(){
        return items;
    }

    @Override
    public String Id() {
        return sessionId;
    }
}
