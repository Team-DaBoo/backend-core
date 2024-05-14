package b172.challenging.common.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlers {

	@ExceptionHandler(CustomRuntimeException.class)
	public ResponseEntity handleBadRequestException(final CustomRuntimeException customRuntimeException) {
		log.error(customRuntimeException.getMessage(), customRuntimeException);
		ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.of(
			customRuntimeException.getExceptions().getStatusCode(),
			customRuntimeException.getExceptions().getErrorCode(),
			customRuntimeException.getMessage()
		);
		return new ResponseEntity<>(exceptionResponseDto,
			HttpStatusCode.valueOf(customRuntimeException.getExceptions().getStatusCode()));
	}
}
