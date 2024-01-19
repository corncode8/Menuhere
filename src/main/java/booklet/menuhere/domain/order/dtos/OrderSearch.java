package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, DELIVERING, COMPLETE , CANCLE]
    private String startTime;
    private String endTime;
}
