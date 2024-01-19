package booklet.menuhere.service;

import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.Payment;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MenuService menuService;


    public Order createOrder(MakeOrderDto makeOrderDto) {
        Order order = new Order();
        Payment payment = new Payment();
        List<OrderMenu> orderMenus = new ArrayList<>();

        payment.createPayment(makeOrderDto.getPayType(), makeOrderDto.getPayStatus(), makeOrderDto.getOrderPrice(), order);

        try {
            if (makeOrderDto.getEmail().equals("Non-Members")) {
                order.createOrder(makeOrderDto.getOrderStatus(), makeOrderDto.getRequests(), makeOrderDto.getOrderPrice(),
                        makeOrderDto.getTableNo(), makeOrderDto.getOrderType(), payment);
            } else {
                Optional<User> emailOpt = userService.findEmail(makeOrderDto.getEmail());
                if (emailOpt.isPresent()) {
                    User user = emailOpt.get();
                    order.createOrder(makeOrderDto.getOrderStatus(), makeOrderDto.getRequests(), makeOrderDto.getOrderPrice(),
                            makeOrderDto.getTableNo(), makeOrderDto.getOrderType(), payment);
                    order.addUser(user);
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

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

}
