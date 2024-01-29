package b172.challenging.common.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private final Exceptions exceptions;

    public CustomRuntimeException(Exceptions exceptions) {
        super(exceptions.getMessage());
        this.exceptions = exceptions;
    }
}