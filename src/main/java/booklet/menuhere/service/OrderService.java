package booklet.menuhere.service;

import booklet.menuhere.domain.Delivery;
import booklet.menuhere.domain.order.dtos.OrderSearchDto;
import booklet.menuhere.domain.order.dtos.OrderQueryDto;
import booklet.menuhere.domain.order.dtos.OrderViewDto;
import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.Payment;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.repository.order.OrderRepository;
import booklet.menuhere.repository.order.query.OrderQueryRepository;
import booklet.menuhere.repository.order.query.OrderSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MenuService menuService;
    private final OrderSearchRepository orderSearchRepository;
    private final OrderQueryRepository orderQueryRepository;


    public Order createOrder(MakeOrderDto makeOrderDto) {
        Order order = new Order();
        Payment payment = new Payment();
        List<OrderMenu> orderMenus = new ArrayList<>();

        payment.createPayment(makeOrderDto.getPayType(), makeOrderDto.getPayStatus(), makeOrderDto.getOrderPrice(), order);

        try {
            if (makeOrderDto.getEmail().equals("Non-Members")) {
                order.createOrder(makeOrderDto.getStatus(), makeOrderDto.getRequests(), makeOrderDto.getOrderPrice(),
                        makeOrderDto.getTableNo(), makeOrderDto.getOrderType(), payment);
            } else {
                Optional<User> emailOpt = userService.findEmail(makeOrderDto.getEmail());
                if (emailOpt.isPresent()) {
                    User user = emailOpt.get();
                    order.createOrder(makeOrderDto.getStatus(), makeOrderDto.getRequests(), makeOrderDto.getOrderPrice(),
                            makeOrderDto.getTableNo(), makeOrderDto.getOrderType(), payment);
                    order.addUser(user);
                    if (makeOrderDto.getOrderType().equals("delivery")) {   // TODO: delivery 추가 test code
                        Delivery delivery = new Delivery();
                        delivery.setDelivery(order, user.getAddress());
                        order.addDelivery(delivery);
                    }
                } else {
                    log.info("!emailOpt.isPresent() : {}", emailOpt.isPresent());
                }
            }

            for (OrderMenuDto orderMenu : makeOrderDto.getOrderMenus()) {
                Optional<Menu> menuOpt = menuService.findById(orderMenu.getMenuId());
                log.info("menuOpt : {}", menuOpt);

                if (menuOpt.isPresent()) {
                    Menu menu =  menuOpt.get();
                    log.info(String.valueOf(menu));
                    int totalPrice = menu.getPrice() * orderMenu.getQuantity();
                    menu.plusOrderNum(orderMenu.getQuantity());
                    OrderMenu newOrderMenu = new OrderMenu(totalPrice, orderMenu.getQuantity(), order, menu);
                    orderMenus.add(newOrderMenu);
                }
            }

            order.setOrderMenus(orderMenus);
            log.info("order : {}", order);
            orderRepository.save(order);
            return order;

        } catch (Exception e) {
            log.info("createOrder Exception : {}", e);
            return null;
        }

    }

    public List<OrderViewDto> findOrders(OrderSearchDto orderSearch) {
        List<OrderQueryDto> queryDtos = orderSearchRepository.findAll_optimization(orderSearch);

        List<OrderViewDto> viewDtos = queryDtos.stream()
                .map(queryDto -> new OrderViewDto(
                        queryDto.getOrderId(),
                        queryDto.getUsername(),
                        queryDto.getOrderMenus(),
                        queryDto.getStatus(),
                        queryDto.getOrderType(),
                        queryDto.getOrderDate()
                ))
                .collect(Collectors.toList());

        return viewDtos;
    }

}
