package booklet.menuhere.service;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.domain.menu.form.MenuAddDto;
import booklet.menuhere.domain.menu.form.MenuEditDto;
import booklet.menuhere.domain.menu.form.MenuViewDto;
import booklet.menuhere.repository.MenuRepository;
import booklet.menuhere.repository.menu.MenuViewDtoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuViewDtoRepository menuViewDtoRepository;
    private final MenuRepository menuRepository;
    private final FileStore fileStore;

    public void addMenu(MenuAddDto form) throws Exception{

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        if (menuRepository.findByName(form.getName()).isPresent()) {
            throw new Exception("이미 존재하는 메뉴 이름입니다.");
        }
        Menu menu = new Menu();
        menu.UpdateMenu(form.getName(), form.getContent(), form.getPrice(), form.getCategory(), false, attachFile);

        menuRepository.save(menu);
    }

    // 메뉴 수정 폼
    public MenuEditDto editView(Long menuId) {
        Optional<Menu> menuOpt = menuRepository.findById(menuId);
        if (menuOpt.isPresent()) {
            Menu menu = menuOpt.get();

            MenuEditDto editDto = new MenuEditDto();
            editDto.setSaleHold(menu.isSaleHold());
            editDto.setCategory(menu.getCategory());
            editDto.setContent(menu.getContent());
            editDto.setPrice(menu.getPrice());
            editDto.setMenuId(menu.getId());
            editDto.setName(menu.getName());
            editDto.setStoreFileName(menu.getUploadFile().getStoreFileName());

            return editDto;
        } else{
            return null;
        }
    }

    // 메뉴 수정하기
    public Boolean editMenu(MenuEditDto editDto) throws Exception{
        Optional<Menu> menuOpt = menuRepository.findById(editDto.getMenuId());

        if (menuOpt.isPresent()) {
            Menu menu = menuOpt.get();

            if (editDto.getAttachFile() != null && !editDto.getAttachFile().isEmpty()) {
                UploadFile attachFile = fileStore.storeFile(editDto.getAttachFile());
                menu.updateFile(attachFile);
            }
            menu.EditMenu(editDto.getName(), editDto.getContent(), editDto.getPrice(), editDto.getCategory(), editDto.isSaleHold());
            menuRepository.save(menu);
            return Boolean.TRUE;
        } else
            return Boolean.FALSE;
    }

    // 매니저 메뉴 view
//    public List<MenuViewDto> managerViewMenu() {
//        List<Menu> menuList = menuRepository.findAllBySaleIsTrue();
//        log.info("Service - manager menu");
//
//        if (menuList.isEmpty()) {
//            return null;
//        }
//        return menuList.stream()
//                .map(menu -> new MenuViewDto(menu))
//                .collect(Collectors.toList());
//    }

    // 유저 메뉴 view
    public List<MenuViewDto> ViewMenu() {
        log.info("View Menu : {}", menuViewDtoRepository.findViewDto());
        return menuViewDtoRepository.findViewDto();
    }


    // 메뉴 제거
//    public void removeMenu(Long menuId) {
//        Optional<Menu> menuOpt = menuRepository.findById(menuId);
//        if (menuOpt.isPresent()) {
//            Menu menu = menuOpt.get();
//            menu.setSale(false);
//            menu.setName("deleteMenu" + UUID.randomUUID());
//        }
//    }

    public List<MenuViewDto> findCategory(Category category) {
        return menuViewDtoRepository.findCategoryView(category);
    }

    public Menu getMenuName(String name) {
        return menuRepository.findByName(name).orElse(null);
    }
}
