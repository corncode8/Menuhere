package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OrderSearchDto {

    @Nullable
    private String userName; //회원 이름
    @Nullable
    private OrderStatus orderStatus; //주문 상태[ORDER, DELIVERING, COMPLETE , CANCLE]
    @Nullable
    private String orderType; // 주문 유형 (예약, 배달, 매장 내 식사)
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
