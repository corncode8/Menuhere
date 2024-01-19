package booklet.menuhere.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, "요청에 성공"),
    SUCCESS_NON_USER(true, "(비회원) 요청에 성공"),

    // 요청 오류
    FAIL_SIGNUP(false, "회원가입에 실패했습니다."),
    LOGIN_EXCEPTION(false, "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(false, "유저를 찾을 수 없습니다."),
    NOT_FOUND_MENU(false, "해당 메뉴를 찾을 수 없습니다."),
    CART_NULL_EXCEPTION(false, "장바구니가 비어있습니다."),
    TABLE_NO_NULL_EXCEPTION(false, "테이블 번호를 입력해주세요."),
    INVALID_TOKEN(false, "유효하지 않은 토큰입니다."),
    EMPTY_CART(false, "장바구니가 비어있습니다."),
    CREATE_ORDER_EXCEPTION(false, "주문 생성중 오류가 발생했습니다.");




    private final boolean isSuccess;
    private final String message;

    //열거 상수에 대한 생성자로, 응답 상태값을 초기화함
    private BaseResponseStatus(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
