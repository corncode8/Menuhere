package booklet.menuhere.service;

import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.domain.menu.dtos.MenuAddDto;
import booklet.menuhere.domain.menu.dtos.MenuEditDto;
import booklet.menuhere.domain.menu.dtos.MenuViewDto;
import booklet.menuhere.repository.menu.MenuRepository;
import booklet.menuhere.repository.menu.query.MenuSearchRepository;
import booklet.menuhere.repository.menu.query.MenuViewDtoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuViewDtoRepository menuViewDtoRepository;
    private final MenuSearchRepository menuSearchRepository;
    private final MenuRepository menuRepository;
    private final FileStore fileStore;

    public Menu addMenu(MenuAddDto form) throws Exception{

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        if (menuRepository.findByName(form.getName()).isPresent()) {
            throw new Exception("이미 존재하는 메뉴 이름입니다.");
        }
        Menu menu = new Menu();
        menu.UpdateMenu(form.getName(), form.getContent(), form.getPrice(), form.getCategory(), false, attachFile);

        return menuRepository.save(menu);
    }

    // 메뉴 수정 폼
    public MenuEditDto editView(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. " + menuId));

        MenuEditDto editDto = new MenuEditDto();
        editDto.setMenuEditDto(menu.getName(), menu.getContent(), menu.getPrice(), menu.isSale(), menu.getUploadFile().getStoreFileName(), menu.getCategory());

        return editDto;

    }

    // 메뉴 수정하기
    public Menu editMenu(MenuEditDto editDto, Long menuId) throws Exception{
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. " + menuId));

        if (editDto.getAttachFile() != null && !editDto.getAttachFile().isEmpty()) {
            UploadFile attachFile = fileStore.storeFile(editDto.getAttachFile());
            menu.updateFile(attachFile);
        }
        if (editDto.isSaleHold() == true) {
            menu.EditMenu(UUID.randomUUID().toString(), editDto.getContent(), editDto.getPrice(), editDto.getCategory(), true);
        } else {
            menu.EditMenu(editDto.getName(), editDto.getContent(), editDto.getPrice(), editDto.getCategory(), editDto.isSaleHold());
        }

        return menuRepository.save(menu);

    }


    // 메뉴 view
    public Page<MenuViewDto> ViewMenu(Pageable pageable) {
        return menuViewDtoRepository.menuViewQuery(pageable);
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

    public Page<MenuViewDto> findCategory(Category category, Pageable pageable) {
        return menuViewDtoRepository.findCategoryViewV2(category, pageable);
    }

    public Page<MenuViewDto> MenuSearch(String search, Pageable pageable) {
        return  menuSearchRepository.findAll(search, pageable);
    }

    public Optional<Menu> findById(Long menuId) {
        return menuRepository.findById(menuId);
    }
    public Menu getMenuName(String name) {
        return menuRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("해당 메뉴를 찾을 수 없습니다. " + name));
    }

}
