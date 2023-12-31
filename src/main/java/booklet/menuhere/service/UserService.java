package booklet.menuhere.service;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.dtos.UserSignUpDto;
import booklet.menuhere.domain.order.dtos.OrderUserInfoDto;
import booklet.menuhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
    public void singUp(UserSignUpDto userSignUpDto) throws Exception {
        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (userRepository.findByusername(userSignUpDto.getUsername()).isPresent()) {
            throw new Exception("이미 존재하는 이름입니다.");
        }
        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .username(userSignUpDto.getUsername())
                .phone(userSignUpDto.getPhone())
                .address(userSignUpDto.getAddress())
                .role(Role.USER)
                .build();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }


    // 로그인 로직
    public User login(String id, String password) {
        return userRepository.findByEmail(id)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Optional<User> findEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Role getRole(String email) {
        Optional<User> user = findEmail(email);
        if (user.isPresent()) {
            return user.get().getRole();
        } else {
            // 적절한 대체 작업 또는 예외 처리
            throw new NoSuchElementException("User not found with email: " + email);
        }
    }

    public Optional<OrderUserInfoDto> OrderUserInfo(String email) {
        return findEmail(email)
                .map(u -> new OrderUserInfoDto(u.getUsername(), u.getAddress(), u.getPhone(), u.getEmail()));
    }
}
