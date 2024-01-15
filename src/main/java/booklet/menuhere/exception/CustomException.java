package booklet.menuhere.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends Exception{
    private BaseResponseStatus status;
}
