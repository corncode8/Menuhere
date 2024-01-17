package booklet.menuhere.service;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.dtos.UserSignUpDto;
import booklet.menuhere.domain.model.Email;
import booklet.menuhere.domain.order.dtos.OrderUserInfoDto;
import booklet.menuhere.exception.CustomException;
import booklet.menuhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        if (userRepository.findByEmailValue(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (userRepository.findByusername(userSignUpDto.getUsername()).isPresent()) {
            throw new Exception("이미 존재하는 이름입니다.");
        }
        User user = User.builder()
                .email(Email.of(userSignUpDto.getEmail()))
                .password(userSignUpDto.getPassword())
                .username(userSignUpDto.getUsername())
                .phone(userSignUpDto.getPhone())
                .address(userSignUpDto.getAddress())
                .build();
        user.passwordEncode(passwordEncoder); user.authorizeUser();
        userRepository.save(user);
    }


    // 로그인 로직
    public User login(String id, String password) throws CustomException{
        // 아이디로 사용자 찾기
        User user = userRepository.findByEmailValue(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 찾을 수 없습니다: " + id));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new EntityNotFoundException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    public Optional<User> findEmail(String email) {
        return userRepository.findByEmailValue(email);
    }

    public Role getRole(String email) {
        User user = findEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 찾을 수 없습니다. " + email));

        return user.getRole();

    }

    public OrderUserInfoDto OrderUserInfo(String email) {
        return findEmail(email)
                .map(u -> new OrderUserInfoDto(u.getUsername(), u.getAddress(), u.getPhone(), u.getEmail().getValue()))
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 찾을 수 없습니다. " + email));
    }

}
