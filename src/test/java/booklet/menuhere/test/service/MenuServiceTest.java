package booklet.menuhere.test.service;

import booklet.menuhere.domain.menu.Menu;
import booklet.menuhere.domain.menu.dtos.MenuAddDto;
import booklet.menuhere.domain.menu.dtos.MenuEditDto;
import booklet.menuhere.domain.menu.file.UploadFile;
import booklet.menuhere.test.IntegrationTest;
import booklet.menuhere.test.config.TestProfile;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(TestProfile.TEST)
@Slf4j
class MenuServiceTest extends IntegrationTest {

    @DisplayName("메뉴 추가 성공")
    @Test
    void addMenu() throws Exception{
        //given
        MenuAddDto addDto = menuSetUp.setDto(0);

        //when
        Menu menu = menuService.addMenu(addDto);

        //then
        log.info(String.valueOf(menu));
        assertThat(menu).isNotNull();
        assertThat(addDto.getName()).isEqualTo(menu.getName());
    }

    @DisplayName("메뉴 추가 실패 (중복된 메뉴이름)")
    @Test
    void failAddMenu() throws Exception{
        //given
        MenuAddDto addDto = menuSetUp.setDto(0);
        menuService.addMenu(addDto);

        // when & then: 같은 이름으로 메뉴 추가를 시도하면 예외 발생
        MenuAddDto addDto1 = menuSetUp.setDto(0);
        assertThrows(Exception.class, () -> menuService.addMenu(addDto1), "이미 존재하는 메뉴 이름입니다.");
    }

    @DisplayName("메뉴 수정 테스트 성공")
    @Test
    void editMenu() throws Exception{
        //given
        Menu menu = menuSetUp.save(0);
        MenuEditDto editDto = new MenuEditDto();

        editDto.setMenuEditDto("editMenuName", "editContent", menu.getPrice(), menu.isSale(), menu.getUploadFile().getStoreFileName(), menu.getCategory());

        //when
        Menu editMenu = menuService.editMenu(editDto, menu.getId());

        //then
        assertThat(editMenu).isNotNull();
        assertThat(editMenu.getName()).isEqualTo(editDto.getName());
        assertThat(editMenu.getContent()).isEqualTo(editDto.getContent());
    }

    @DisplayName("메뉴 수정 테스트 성공 (isSale == true)")
    @Test
    void editMenuSaleTrue() throws Exception{
        //given
        Menu menu = menuSetUp.save(0);
        MenuEditDto editDto = new MenuEditDto();

        editDto.setMenuEditDto("editMenuName", "editContent", menu.getPrice(), true, menu.getUploadFile().getStoreFileName(), menu.getCategory());

        //when
        Menu editMenu = menuService.editMenu(editDto, menu.getId());

        //then
        assertThat(editMenu).isNotNull();

        log.info(editMenu.getName());
        assertThat(editMenu.getName()).isNotEqualTo(editDto.getName());

        assertThat(editMenu.getContent()).isEqualTo(editDto.getContent());
    }

    @DisplayName("메뉴 수정 테스트 실패 (NotFoundMenuId)")
    @Test
    void editMenuNotFoundMenuId() throws Exception{
        //given
        Menu menu = menuSetUp.save(0);
        MenuEditDto editDto = new MenuEditDto();

        long wrongMenuId = menu.getId() + 1;
        editDto.setMenuEditDto("editMenuName", "editContent", menu.getPrice(), true, menu.getUploadFile().getStoreFileName(), menu.getCategory());

        // when & then
        assertThrows(EntityNotFoundException.class, () ->
        { menuService.editMenu(editDto, wrongMenuId); }
                ,"해당 메뉴를 찾을 수 없습니다. " + wrongMenuId);
    }

    @DisplayName("메뉴 return")
    @Test
    void getMenuName() throws Exception{
        //given
        Menu menu = menuSetUp.save(0);

        // when
        Menu menuName = menuService.getMenuName(menu.getName());

        // then
        assertThat(menuName).isNotNull();
        assertThat(menu).isEqualTo(menuName);
    }

    @DisplayName("메뉴 return 실패")
    @Test
    void failGetMenuName() {
        //given
        String wrongMenuName = "잘못된 메뉴이름";

        // when & then
        assertThrows(EntityNotFoundException.class, () ->
                { menuService.getMenuName(wrongMenuName); }
                ,"해당 메뉴를 찾을 수 없습니다. " + wrongMenuName);
    }

}
