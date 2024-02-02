package b172.challenging.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleBadRequestException(final CustomRuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(ExceptionResponseDto.of(
                        e.getErrorCode().getStatusCode(),
                        e.getErrorCode().getErrorCode(),
                        e.getMessage())
                );
    }
}