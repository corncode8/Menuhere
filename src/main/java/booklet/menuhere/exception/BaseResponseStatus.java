package booklet.menuhere.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, "요청에 성공"),
    SUCCESS_NON_USER(true, "(비회원) 요청에 성공"),

    // 요청 오류
    LOGIN_EXCEPTION(false, "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(false, "유저를 찾을 수 없습니다.");



    private final boolean isSuccess;
    private final String message;

    //열거 상수에 대한 생성자로, 응답 상태값을 초기화함
    private BaseResponseStatus(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
