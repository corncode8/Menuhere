package booklet.menuhere.test;

import booklet.menuhere.config.jwt.JwtService;
import booklet.menuhere.controller.UserController;
import booklet.menuhere.repository.PaymentRepository;
import booklet.menuhere.repository.UserRepository;
import booklet.menuhere.repository.order.OrderRepository;
import booklet.menuhere.service.MenuService;
import booklet.menuhere.service.OrderService;
import booklet.menuhere.service.UserService;
import booklet.menuhere.test.domain.MenuSetUp;
import booklet.menuhere.test.domain.OrderSetUp;
import booklet.menuhere.test.domain.UserSetUp;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SpringBootTest(properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/application-jwt.yml" +
        ",classpath:/application-oauth.yml"
)
@Transactional
public class IntegrationTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserSetUp userSetUp;

    @Autowired
    public MenuSetUp menuSetUp;

    @Autowired
    public OrderSetUp orderSetUp;

    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public MenuService menuService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserController userController;

    @Autowired
    public JwtService jwtService;

    @Mock
    public HttpServletResponse response;

    @Mock
    public HttpServletRequest request;

}
