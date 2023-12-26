package booklet.menuhere.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private T result;
}
