package b172.challenging.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponseDto {
	private final int statusCode;
	private final String errorCode;
	private final String message;

	public static ExceptionResponseDto of(int statusCode, String errorCode, String message) {
		return new ExceptionResponseDto(statusCode, errorCode, message);
	}
}
