package b172.challenging.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity handleBadRequestException(final CustomRuntimeException e) {
        log.error(e.getMessage(), e);
        ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.of(
                e.getExceptions().getStatusCode(),
                e.getExceptions().getErrorCode(),
                e.getMessage()
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatusCode.valueOf(e.getExceptions().getStatusCode()));
    }
}