package b172.challenging.common.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}