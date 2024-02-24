package booklet.menuhere.test.domain;

import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.repository.order.OrderRepository;
import booklet.menuhere.service.OrderService;
import booklet.menuhere.test.config.TestProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile(TestProfile.TEST)
@Component
public class OrderSetUp {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    public void save(int n) {
        MakeOrderDto makeOrderDto = new MakeOrderDto(OrderStatus.ORDER, "testRequests", 50000, "dine-in", "Kakaopay", "결제 완료", setOrderMenuDto(n),
                5, "Non-Members");

        MakeOrderDto dto = dtoBuild(makeOrderDto, n);

        for (int i = 0; i < n; i++) {
            Order order = orderService.createOrder(dto);
            orderRepository.save(order);
        }
    }

    public void save2(int n) {
        MakeOrderDto makeOrderDto = new MakeOrderDto(OrderStatus.DELIVERING, "test", 51234, "reservation", "Kakaopay", "결제 완료", setOrderMenuDto(n),
                7, "Non-Members");

        MakeOrderDto dto = dtoBuild(makeOrderDto, n);

        for (int i = 0; i < n; i++) {
            Order order = orderService.createOrder(dto);
            orderRepository.save(order);
        }
    }

    public void save3(int n) {
        MakeOrderDto makeOrderDto = new MakeOrderDto(OrderStatus.COMPLETE, "testReq", 5550, "delivery", "Kakaopay", "결제 전", setOrderMenuDto(n),
                2, "Non-Members");

        MakeOrderDto dto = dtoBuild(makeOrderDto, n);

        for (int i = 0; i < n; i++) {
            Order order = orderService.createOrder(dto);
            orderRepository.save(order);
        }
    }

    private List<OrderMenuDto> setOrderMenuDto (int n) {
        List<OrderMenuDto> menuDtos = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            OrderMenuDto menuDto = new OrderMenuDto();
            Long id = (long) (Math.random() * (338 - 162 + 1)) + 162;
            menuDto.setMenuId(id);
            menuDto.setQuantity(n);
            menuDtos.add(menuDto);
        }
        return menuDtos;
    }


    private MakeOrderDto dtoBuild(MakeOrderDto orderDto, int num) {
        return new MakeOrderDto(orderDto.getStatus(), orderDto.getRequests() + num,
                orderDto.getOrderPrice(), orderDto.getOrderType(), orderDto.getPayType(), orderDto.getPayStatus(), orderDto.getOrderMenus(),
                orderDto.getTableNo() + num, orderDto.getEmail());
    }

}
