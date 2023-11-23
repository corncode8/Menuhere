package booklet.menuhere.controller;

import booklet.menuhere.domain.User.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class IndexController {
    @GetMapping("/")
    public String main() {
        return "main";
    }
    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/whouser")
    public User whoUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        log.info("user : {}", user);
        return user;
    }
}
