package booklet.menuhere.domain.order.dtos;

import booklet.menuhere.domain.OrderStatus;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;


@Data
public class OrderSearchDto {

    @Nullable
    private String userName; //회원 이름
    @Nullable
    private OrderStatus status; //주문 상태[ORDER, DELIVERING, COMPLETE , CANCLE]
    @Nullable
    private String orderType; // 주문 유형 (예약, 배달, 매장 내 식사)

    @DateTimeFormat(pattern = "yyyy-MM-dd") @Nullable
    private LocalDate startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd") @Nullable
    private LocalDate endTime;

    public Boolean isEmpty() {
        return StringUtils.isEmpty(this.userName) &&
                this.status == null &&
                StringUtils.isEmpty(this.orderType) &&
                this.startTime == null &&
                this.endTime == null;
    }
}
