package booklet.menuhere.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static booklet.menuhere.exception.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess; //응답 성공 여부
    private final String message; //응답 메시지

    @JsonInclude(JsonInclude.Include.NON_NULL) //'result'필드가 null이면 출력하지 않도록
    private T result;

    //요청 성공
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess(); //성공 여부 true로 설정
        this.message = SUCCESS.getMessage(); //성공 메시지 설정
        this.result = result; //응답 결과 데이터 설정
    }

    //요청 실패
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess=status.isSuccess(); //실패상태 설정
        this.message = status.getMessage(); //실패 메시지 설정

    }
}
