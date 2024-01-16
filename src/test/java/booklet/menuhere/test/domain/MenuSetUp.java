package booklet.menuhere.test.domain;

import booklet.menuhere.domain.menu.Category;
import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.dtos.MenuAddDto;
import booklet.menuhere.domain.menu.file.FileStore;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.repository.menu.MenuRepository;
import booklet.menuhere.test.config.TestProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Profile(TestProfile.TEST)
@Component
public class MenuSetUp {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private FileStore fileStore;

    public void save(int num) throws Exception{
        for (int i = 0; i < num; i++) {
            Menu menu = setMenu(setDto(i));
            menuRepository.save(menu);
        }
    }

    private Menu setMenu(MenuAddDto menuAddDto) throws Exception{
        Menu menu = new Menu();

        UploadFile attachFile = fileStore.storeFile(menuAddDto.getAttachFile());

        menu.UpdateMenu(menuAddDto.getName(), menuAddDto.getContent(), menuAddDto.getPrice(), menuAddDto.getCategory(), false, attachFile);
        return menu;
    }

    public MenuAddDto setDto(int cnt) {
        MenuAddDto addDto = new MenuAddDto();

        byte[] contentBytes = ("테스트 사진" + cnt).getBytes();
        MultipartFile attachFile = new MockMultipartFile("file", "filename.jpg", "jpg/plain", contentBytes);

        addDto.setName("testMenuName" + " " +  cnt);

        if (cnt % Math.sqrt(cnt) == 0) {
            addDto.setCategory(Category.SIGNATURE);
        } else {
            addDto.setCategory(Category.BEVERAGE);
        }

        addDto.setPrice(cnt + 10000);
        addDto.setContent("testContent" + " " + cnt);
        addDto.setAttachFile(attachFile);

        return addDto;
    }
}
