package booklet.menuhere.service;

import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.domain.menu.form.MenuAddDTO;
import booklet.menuhere.domain.menu.form.MenuViewDTO;
import booklet.menuhere.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileStore fileStore;

    public void addMenu(MenuAddDTO form) throws Exception{

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        if (menuRepository.findByName(form.getName()).isPresent()) {
            throw new Exception("이미 존재하는 메뉴 이름입니다.");
        }
        Menu menu = new Menu();
        menu.setName(form.getName());
        menu.setContent(form.getContent());
        menu.setPrice(form.getPrice());
        menu.setUploadFile(attachFile);
        menu.setCategory(form.getCategory());

        menuRepository.save(menu);
    }

    // 모든 메뉴 return
    public List<MenuViewDTO> viewMenu() {
        List<Menu> menuList = menuRepository.findAll();

        if (menuList.isEmpty()) {
            return null;
        }
        return menuList.stream()
                .map(menu -> {
                    MenuViewDTO menuViewForm = new MenuViewDTO();
                    menuViewForm.setName(menu.getName());
                    menuViewForm.setCategory(menu.getCategory());
                    menuViewForm.setPrice(menu.getPrice());
                    menuViewForm.setUploadFile(menu.getUploadFile());
                    menuViewForm.setContent(menu.getContent());
                    menuViewForm.setMenuId(menu.getId());
                    return menuViewForm;
                }).collect(Collectors.toList());

    }
}
