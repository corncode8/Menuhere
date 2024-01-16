package booklet.menuhere.test.domain;

import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.domain.orderStatus;
import booklet.menuhere.domain.ordermenu.OrderMenu;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.repository.order.OrderRepository;
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
    private OrderRepository orderRepository;


}
