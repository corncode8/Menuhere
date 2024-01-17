package booklet.menuhere.test.domain;

import booklet.menuhere.repository.order.OrderRepository;
import booklet.menuhere.test.config.TestProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(TestProfile.TEST)
@Component
public class OrderSetUp {

    @Autowired
    private OrderRepository orderRepository;


}
