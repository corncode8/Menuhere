package booklet.menuhere.controller;

import booklet.menuhere.domain.User.Address;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.UserSignUpDto;
import booklet.menuhere.repository.UserRepository;
import booklet.menuhere.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//class UserControllerTest {
//
//    @Autowired
//    public UserService userService;
//    @Autowired
//    public UserRepository userRepository;
//    EntityManager em;
//    @Test
//    void signUp() throws Exception {
//        UserSignUpDto userdto = new UserSignUpDto("sdfs", "adasd", "adasd", "asda",
//                new Address("asda", "asdad", "asdasd"));
//        userService.singUp(userdto);
//        Optional<User> adasd = userRepository.findByusername("adasd");
//
//
//    }
//
//    @Test
//    void testSignUp() {
//    }
//
//    @Test
//    void jwtTest() {
//    }
//}