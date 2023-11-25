package booklet.menuhere.service;

import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.domain.menu.form.MenuAddForm;
import booklet.menuhere.domain.menu.form.MenuViewForm;
import booklet.menuhere.repository.MenuRepository;
import booklet.menuhere.repository.UploadFileRepository;
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
    private final UploadFileRepository uploadFileRepository;
    private final FileStore fileStore;

    public void addMenu(MenuAddForm form) throws Exception{

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        if (menuRepository.findByName(form.getName()).isPresent()) {
            throw new Exception("이미 존재하는 메뉴 이름입니다.");
        }
        Menu menu = new Menu();
        UploadFile uploadFile = new UploadFile();
        menu.setName(form.getName());
        menu.setContent(form.getContent());
        menu.setPrice(form.getPrice());
        uploadFile.setStoreFileName(attachFile.getStoreFileName());
        uploadFile.setUploadFileName(attachFile.getUploadFileName());

        uploadFileRepository.save(uploadFile);
        menuRepository.save(menu);
    }

    // 모든 메뉴 return
    public List<MenuViewForm> viewMenu() {
        List<Menu> menuList = menuRepository.findAll();
        if (menuList.isEmpty()) {
            return null;
        }
        List<MenuViewForm> menus = menuList.stream()
                .map(menu -> new MenuViewForm())
                .collect(Collectors.toList());
        return menus;
    }
}
