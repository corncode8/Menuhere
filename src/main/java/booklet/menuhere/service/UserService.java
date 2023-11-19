package booklet.menuhere.service;

import booklet.menuhere.domain.Role;
import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.User.UserSignUpDto;
import booklet.menuhere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .phone(userSignUpDto.getPhone())
                .address(userSignUpDto.getAddress())
                .role(Role.USER)
                .build();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}
