package booklet.menuhere.test;

import booklet.menuhere.repository.UserRepository;
import booklet.menuhere.service.UserService;
import booklet.menuhere.test.domain.UserSetUp;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class IntegrationTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserSetUp userSetUp;

    @Mock
    public PasswordEncoder passwordEncoder;
}
