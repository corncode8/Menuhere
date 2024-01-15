package booklet.menuhere.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, "요청에 성공"),

    // 요청 오류
    UserNotFoundException(false, "가입되지 않은 아이디 입니다."),
    InvalidPasswordException(false, "비밀번호가 일치하지 않습니다.");


    private final boolean isSuccess;
    private final String message;

    //열거 상수에 대한 생성자로, 응답 상태값을 초기화함
    private BaseResponseStatus(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
