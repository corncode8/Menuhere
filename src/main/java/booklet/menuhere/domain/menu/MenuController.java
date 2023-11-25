package booklet.menuhere.domain.menu;

import booklet.menuhere.domain.menu.form.MenuAddForm;
import booklet.menuhere.domain.menu.form.MenuViewForm;
import booklet.menuhere.repository.MenuRepository;
import booklet.menuhere.repository.UploadFileRepository;
import booklet.menuhere.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;
    private final UploadFileRepository uploadFileRepository;


    @Value("${file.dir}")
    private String fileDir;

    private final MenuService menuService;

    @GetMapping("/addform")
    public String addForm(Model model) {
        model.addAttribute("menu", new MenuAddForm());
        return "form/addform";
    }

    @PostMapping("/add")
    public String addMenu(@Validated @ModelAttribute("menu") MenuAddForm form, BindingResult result,
                          MultipartFile file, HttpServletRequest request) throws Exception{
        if (result.hasErrors()) {
            log.info("errors = {}", result);
            return "/menu";
        }
        log.info("request = {}", request);
        log.info("multipartFile = {}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath = {}", fullPath);
            file.transferTo(new File(fullPath));
        }

        menuService.addMenu(form);
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu(Model model, BindingResult result) {
        if (result.hasErrors()) {
            log.info("errors = {}", result);
            return "/menu";
        }
        List<MenuViewForm> forms = menuService.viewMenu();
        model.addAttribute("menus", forms);

        return "menu";
    }


}
