package booklet.menuhere.test.service;


import booklet.menuhere.domain.User.User;
import booklet.menuhere.domain.order.Order;
import booklet.menuhere.domain.order.dtos.MakeOrderDto;
import booklet.menuhere.domain.OrderStatus;
import booklet.menuhere.domain.order.dtos.OrderSearchDto;
import booklet.menuhere.domain.order.dtos.OrderViewDto;
import booklet.menuhere.domain.ordermenu.dtos.OrderMenuDto;
import booklet.menuhere.test.IntegrationTest;
import booklet.menuhere.test.config.TestProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(TestProfile.TEST)
@Slf4j
class OrderServiceTest extends IntegrationTest {

    @DisplayName("주문 생성 테스트 성공 (회원)")
    @Test
    void userOrder() {
        //given
        User user = userSetUp.save();
        MakeOrderDto orderDto = new MakeOrderDto(OrderStatus.ORDER, "test 요청사항", 52000, "Dine-in",
                "KakaoPay", "결제 완료", getMenuDtos(), 5, user.getEmail().getValue());


        //when
        Order order = orderService.createOrder(orderDto);

        //then
        log.info(String.valueOf(order));

        assertThat(order).isNotNull();
        assertThat(order.getUser()).isEqualTo(user);

        assertThat(order.getStatus()).isEqualTo(orderDto.getStatus());
        assertThat(order.getRequests()).isEqualTo(orderDto.getRequests());
        assertThat(order.getOrderPrice()).isEqualTo(orderDto.getOrderPrice());
        assertThat(order.getOrderMenus()).hasSameSizeAs(orderDto.getOrderMenus());
        assertThat(order.getTableNo()).isEqualTo(orderDto.getTableNo());

        assertThat(order.getPayment().getPayStatus()).isEqualTo(orderDto.getPayStatus());
        assertThat(order.getPayment().getOrderPrice()).isEqualTo(orderDto.getOrderPrice());
        assertThat(order.getPayment().getPayType()).isEqualTo(orderDto.getPayType());

    }

    @DisplayName("주문 생성 테스트 성공 (비회원)")
    @Test
    void nonUserOrder() {
        //given
        MakeOrderDto orderDto = new MakeOrderDto(OrderStatus.ORDER, "test 요청사항", 52000, "Dine-in",
                "KakaoPay", "결제 완료", getMenuDtos(), 5, "Non-Members");


        //when
        Order order = orderService.createOrder(orderDto);

        //then
        log.info(String.valueOf(order));

        assertThat(order).isNotNull();
        assertThat(order.getUser()).isNull();

        assertThat(order.getStatus()).isEqualTo(orderDto.getStatus());
        assertThat(order.getRequests()).isEqualTo(orderDto.getRequests());
        assertThat(order.getOrderPrice()).isEqualTo(orderDto.getOrderPrice());
        assertThat(order.getOrderMenus()).hasSameSizeAs(orderDto.getOrderMenus());
        assertThat(order.getTableNo()).isEqualTo(orderDto.getTableNo());

        assertThat(order.getPayment().getPayStatus()).isEqualTo(orderDto.getPayStatus());
        assertThat(order.getPayment().getOrderPrice()).isEqualTo(orderDto.getOrderPrice());
        assertThat(order.getPayment().getPayType()).isEqualTo(orderDto.getPayType());

    }

    @DisplayName("주문 생성 테스트 실패 (테이블번호 null)")
    @Test
    void failOrder() {
        //given
        MakeOrderDto orderDto = new MakeOrderDto(OrderStatus.ORDER, "test 요청사항", 52000, "Dine-in",
                "KakaoPay", "결제 완료", getMenuDtos(), null, "Non-Members");


        //when
        Order order = orderService.createOrder(orderDto);

        //then
        log.info(String.valueOf(order));

        assertThat(order).isNull();
    }


    public List<OrderMenuDto> getMenuDtos() {
        List<OrderMenuDto> menuDtos = new ArrayList<>();

        OrderMenuDto menuDto = new OrderMenuDto();
        menuDto.setMenuId(121L);
        menuDto.setQuantity(5);
        menuDtos.add(menuDto);

        menuDto.setMenuId(122L);
        menuDto.setQuantity(10);
        menuDtos.add(menuDto);
        return menuDtos;
    }

    private OrderSearchDto setDto() {
        OrderSearchDto searchDto = new OrderSearchDto();
        searchDto.setUserName("uniqueUser");
        searchDto.setStatus(OrderStatus.ORDER);
        searchDto.setOrderType("dine-in");
        return searchDto;
    }


    @DisplayName("주문내역 조회 테스트 (data JPA)")
    @Test
    void spring_data_JPA_Search() {
        //given
        OrderSearchDto searchDto = setDto();

        //when
        long startTime = System.currentTimeMillis();

        List<OrderViewDto> viewDtos = orderService.dataJpaSimpleOrders(searchDto);

        long endTime = System.currentTimeMillis();
        System.out.println("(data JPA) Execution time: " + (endTime - startTime) + "ms");
        log.info("viewDtos.size = " + viewDtos.size());
        // 5.4 sec

    }
    @DisplayName("주문내역 조회 테스트 (Query)")
    @Test
    void Query_Search() {
        //given
        OrderSearchDto searchDto = setDto();

        //when
        long startTime = System.currentTimeMillis();

        List<OrderViewDto> viewDtos = orderService.QueryOrders(searchDto);

        long endTime = System.currentTimeMillis();
        System.out.println("(Query) Execution time: " + (endTime - startTime) + "ms");
        log.info("viewDtos.size = " + viewDtos.size());
        // 5.2 sec

    }

    @DisplayName("주문내역 조회 테스트 (QueryDSL)")
    @Test
    void optimization_Search() {
        //given
        OrderSearchDto searchDto = setDto();

        //when
        long startTime = System.currentTimeMillis();

        List<OrderViewDto> viewDtos = orderService.findOrders(searchDto);

        long endTime = System.currentTimeMillis();
        System.out.println("(QueryDSL) Execution time: " + (endTime - startTime) + "ms");
        log.info("viewDtos.size = " + viewDtos.size());
        // 2.8 sec

    }


    @DisplayName("테스트")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void 테스트() {
        orderSetUp.save(5000);
        orderSetUp.save2(5000);
        orderSetUp.save3(5000);
    }
}
