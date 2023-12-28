package booklet.menuhere.controller;

import booklet.menuhere.domain.cart.form.CartDto;
import booklet.menuhere.domain.cart.form.CartListDto;
import booklet.menuhere.domain.cart.form.CartViewForm;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String mainpage() {
        return "main";
    }




}
