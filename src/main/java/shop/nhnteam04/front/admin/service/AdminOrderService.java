package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.feign.order.AdminOrderFeignClient;
import shop.nhnteam04.front.order.dto.orders.response.OrderResponse;
import shop.nhnteam04.front.order.dto.orders.response.OrderStatus;


@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final AdminOrderFeignClient orderFeignClient;

    public Page<OrderResponse> getPendingOrders(Pageable pageable){
        return orderFeignClient.getStatusOrders(OrderStatus.PENDING.toString(), pageable);
    }

    // 배송 중으로 전환
    public void shippedOrder(Long orderId){
        if(orderId == null || orderId <= 0){
            throw new IllegalArgumentException();
        }
        orderFeignClient.shippedOrder(orderId);
    }

}
