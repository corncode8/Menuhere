package booklet.menuhere.service;

import booklet.menuhere.domain.Delivery;
import booklet.menuhere.domain.order.dtos.*;
import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.Payment;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.repository.order.OrderRepository;
import booklet.menuhere.repository.order.query.OrderSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
                }
            }

            for (OrderMenuDto orderMenu : makeOrderDto.getOrderMenus()) {
                Optional<Menu> menuOpt = menuService.findById(orderMenu.getMenuId());
//                log.info("menuOpt : {}", menuOpt);

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
//            log.info("order : {}", order);
            orderRepository.save(order);
            return order;

        } catch (Exception e) {
            log.info("createOrder Exception : {}", e);
            return null;
        }

    }

    // 최적화 쿼리
    public Page<OrderViewDto> findOrders(OrderSearchDto orderSearch, Pageable pageable) {
        Page<OrderQueryDto> queryDtos = orderSearchRepository.findAll_optimization(orderSearch, pageable);

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

        return new PageImpl<>(viewDtos, pageable, queryDtos.getTotalElements());
    }

    // spring dataJPA
    public List<OrderViewDto> dataJpaSimpleOrders(OrderSearchDto searchDto) {
        List<Order> orders = orderRepository.findByUserUsernameContainingAndStatusAndOrderType(
                searchDto.getUserName(),
                searchDto.getStatus(),
                searchDto.getOrderType()
        );

        return orders.stream().map(this::convertToOrderViewDto).collect(Collectors.toList());

    }

    // @Query
    public List<OrderViewDto> QueryOrders(OrderSearchDto searchDto) {
        List<Order> orders = orderRepository.findOrdersWithDetails(
                searchDto.getUserName(),
                searchDto.getStatus(),
                searchDto.getOrderType()
        );

        return orders.stream().map(this::convertToOrderViewDto).collect(Collectors.toList());

    }


    private OrderViewDto convertToOrderViewDto(Order order) {
        List<OrderViewMenuDto> menuDtos = order.getOrderMenus().stream()
                .map(orderMenu -> new OrderViewMenuDto(
                        orderMenu.getOrder().getId(),
                        orderMenu.getMenu().getName(),
                        orderMenu.getTotalPrice(),
                        orderMenu.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderViewDto(
                order.getId(),
                order.getUser().getUsername(),
                menuDtos,
                order.getStatus(),
                order.getOrderType(),
                order.getCreatedDate()
        );
    }
}
