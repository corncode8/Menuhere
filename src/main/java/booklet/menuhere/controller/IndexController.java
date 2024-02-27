package booklet.menuhere.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String mainpage() {
        return "main";
    }

    @GetMapping("/check")
    @ResponseBody
    public String testpage() {
        return "CI/CD test success";
    }

}
